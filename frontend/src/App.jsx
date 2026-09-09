import { useCallback, useEffect, useRef, useState } from 'react'

const directions = {
  ArrowUp: 'UP', w: 'UP', W: 'UP',
  ArrowDown: 'DOWN', s: 'DOWN', S: 'DOWN',
  ArrowLeft: 'LEFT', a: 'LEFT', A: 'LEFT',
  ArrowRight: 'RIGHT', d: 'RIGHT', D: 'RIGHT',
}

const apiBase = import.meta.env.VITE_API_URL ?? ''

function createLocalGame() {
  const width = 20
  const height = 20
  const snake = [{ x: 10, y: 10 }]
  let food
  do food = { x: Math.floor(Math.random() * width), y: Math.floor(Math.random() * height) }
  while (food.x === 10 && food.y === 10)
  return { width, height, snake, food, direction: 'RIGHT', score: 0, gameOver: false }
}

const opposite = { UP: 'DOWN', DOWN: 'UP', LEFT: 'RIGHT', RIGHT: 'LEFT' }
const vectors = { UP: [0, -1], DOWN: [0, 1], LEFT: [-1, 0], RIGHT: [1, 0] }

function localMove(game, requestedDirection) {
  if (game.gameOver) return game
  const direction = opposite[game.direction] === requestedDirection ? game.direction : requestedDirection
  const [dx, dy] = vectors[direction]
  const head = { x: game.snake[0].x + dx, y: game.snake[0].y + dy }
  const grows = head.x === game.food.x && head.y === game.food.y
  const body = grows ? game.snake : game.snake.slice(0, -1)
  const collision = head.x < 0 || head.x >= game.width || head.y < 0 || head.y >= game.height
    || body.some(part => part.x === head.x && part.y === head.y)
  if (collision) return { ...game, direction, gameOver: true }

  const snake = [head, ...body]
  let food = game.food
  if (grows) {
    do food = { x: Math.floor(Math.random() * game.width), y: Math.floor(Math.random() * game.height) }
    while (snake.some(part => part.x === food.x && part.y === food.y))
  }
  return { ...game, snake, food, direction, score: game.score + (grows ? 1 : 0) }
}

const relativeActions = ['STRAIGHT', 'TURN_LEFT', 'TURN_RIGHT']
const leftOf = { UP: 'LEFT', LEFT: 'DOWN', DOWN: 'RIGHT', RIGHT: 'UP' }
const rightOf = { UP: 'RIGHT', RIGHT: 'DOWN', DOWN: 'LEFT', LEFT: 'UP' }

function absoluteDirection(direction, action) {
  if (action === 'TURN_LEFT') return leftOf[direction]
  if (action === 'TURN_RIGHT') return rightOf[direction]
  return direction
}

function isDanger(game, direction) {
  const [dx, dy] = vectors[direction]
  const head = { x: game.snake[0].x + dx, y: game.snake[0].y + dy }
  const grows = head.x === game.food.x && head.y === game.food.y
  const body = grows ? game.snake : game.snake.slice(0, -1)
  return head.x < 0 || head.x >= game.width || head.y < 0 || head.y >= game.height
    || body.some(part => part.x === head.x && part.y === head.y)
}

function stateKey(game) {
  const head = game.snake[0]
  const directions = [game.direction, leftOf[game.direction], rightOf[game.direction]]
  return [
    ...directions.map(direction => Number(isDanger(game, direction))),
    Number(game.food.y < head.y), Number(game.food.y > head.y),
    Number(game.food.x < head.x), Number(game.food.x > head.x),
  ].join('')
}

function loadLearningMemory() {
  try { return JSON.parse(localStorage.getItem('snake-q-table-v1')) ?? {} }
  catch { return {} }
}

function learningMove(game, qTable) {
  const oldState = stateKey(game)
  const values = qTable[oldState] ?? [0, 0, 0]
  const explore = Math.random() < 0.12
  const bestValue = Math.max(...values)
  const bestIndexes = values.map((value, index) => value === bestValue ? index : -1).filter(index => index >= 0)
  const actionIndex = explore
    ? Math.floor(Math.random() * relativeActions.length)
    : bestIndexes[Math.floor(Math.random() * bestIndexes.length)]
  const direction = absoluteDirection(game.direction, relativeActions[actionIndex])
  const nextGame = localMove(game, direction)
  const reward = nextGame.gameOver ? -100 : nextGame.score > game.score ? 50 : -0.1
  const futureValues = qTable[stateKey(nextGame)] ?? [0, 0, 0]
  const target = reward + (nextGame.gameOver ? 0 : 0.9 * Math.max(...futureValues))
  const learned = values[actionIndex] + 0.15 * (target - values[actionIndex])
  qTable[oldState] = values.map((value, index) => index === actionIndex ? learned : value)
  localStorage.setItem('snake-q-table-v1', JSON.stringify(qTable))
  return nextGame
}

async function request(path, options) {
  const response = await fetch(`${apiBase}${path}`, options)
  if (!response.ok) throw new Error('Das Backend ist nicht erreichbar.')
  return response.json()
}

export default function App() {
  const [game, setGame] = useState(null)
  const [aiMode, setAiMode] = useState(false)
  const [started, setStarted] = useState(false)
  const [error, setError] = useState('')
  const [localMode, setLocalMode] = useState(false)
  const busy = useRef(false)
  const aiModeRef = useRef(false)
  const manualDirection = useRef('RIGHT')
  const qTable = useRef(loadLearningMemory())

  const setMode = (enabled) => {
    aiModeRef.current = enabled
    if (!enabled && game?.direction) manualDirection.current = game.direction
    setAiMode(enabled)
  }

  const load = useCallback(async () => {
    try {
      setGame(await request('/api/game'))
      setError('')
    } catch {
      setLocalMode(true)
      setGame(createLocalGame())
      setError('')
    }
  }, [])

  const action = useCallback(async (path, body) => {
    if (busy.current) return
    busy.current = true
    if (localMode) {
      setGame(current => path.endsWith('/new')
        ? createLocalGame()
        : path.endsWith('/ai-step')
          ? learningMove(current, qTable.current)
          : localMove(current, body.direction))
      busy.current = false
      return
    }
    try {
      setGame(await request(path, {
        method: 'POST',
        headers: body ? { 'Content-Type': 'application/json' } : undefined,
        body: body ? JSON.stringify(body) : undefined,
      }))
      setError('')
    } catch (err) { setError(err.message) }
    finally { busy.current = false }
  }, [localMode])

  useEffect(() => { load() }, [load])

  useEffect(() => {
    const onKeyDown = (event) => {
      const direction = directions[event.key]
      if (!direction || aiMode) return
      event.preventDefault()
      manualDirection.current = direction
    }
    window.addEventListener('keydown', onKeyDown)
    return () => window.removeEventListener('keydown', onKeyDown)
  }, [action, aiMode])

  useEffect(() => {
    if (!started || !game || game.gameOver) return
    const timer = setInterval(() => {
      if (aiModeRef.current) action('/api/game/ai-step')
      else action('/api/game/move', { direction: manualDirection.current })
    }, 180)
    return () => clearInterval(timer)
  }, [action, aiMode, game?.gameOver, started])

  useEffect(() => {
    if (!started || !aiMode || !game?.gameOver) return
    const timer = setTimeout(restart, 650)
    return () => clearTimeout(timer)
  }, [aiMode, game?.gameOver, started])

  const restart = async () => {
    manualDirection.current = 'RIGHT'
    await action('/api/game/new')
    setStarted(true)
  }

  return (
    <main className="page-shell">
      <section className="hero-copy">
        <div><p className="eyebrow">PLAYGROUND 01</p><h2>Think. Move.<br/><em>Grow.</em></h2></div>
        <p className="intro">Steuere die Schlange selbst oder überlasse das Spielfeld dem KI-Autopiloten.</p>
      </section>

      <section className="game-layout">
        <div className="board-wrap">
          {game ? <Board game={game} /> : <div className="loading">Spiel wird geladen …</div>}
          {game?.gameOver && <div className="game-over"><p>RUN BEENDET</p><strong>Game Over</strong><button onClick={restart}>Noch einmal</button></div>}
        </div>

        <aside>
          <div className="score-card"><span>SCORE</span><strong>{String(game?.score ?? 0).padStart(2, '0')}</strong><small>Länge {game?.snake.length ?? 1}</small></div>
          <div className="panel">
            <span className="label">MODUS</span>
            <div className="mode-switch">
              <button className={!aiMode ? 'active' : ''} onClick={() => setMode(false)}>DU</button>
              <button className={aiMode ? 'active' : ''} onClick={() => setMode(true)}>KI</button>
            </div>
            <p>{aiMode ? 'Q-Learning lernt dauerhaft in diesem Browser.' : 'Ändere die Richtung mit Pfeiltasten oder WASD.'}</p>
          </div>
          <Controls disabled={aiMode || !started} onMove={(direction) => { manualDirection.current = direction }} />
          <button className="restart" onClick={restart}>↻ {started ? 'Neues Spiel' : 'Spiel starten'}</button>
        </aside>
      </section>
      {error && <div className="error">{error} Starte zuerst das Spring-Boot-Backend auf Port 8080.</div>}
    </main>
  )
}

function Board({ game }) {
  const occupied = new Map(game.snake.map((part, index) => [`${part.x}:${part.y}`, index]))
  const cells = []
  for (let y = 0; y < game.height; y++) {
    for (let x = 0; x < game.width; x++) {
      const snakeIndex = occupied.get(`${x}:${y}`)
      const food = game.food.x === x && game.food.y === y
      const className = food ? 'cell food' : snakeIndex === 0 ? 'cell head' : snakeIndex !== undefined ? 'cell snake' : 'cell'
      cells.push(<div className={className} key={`${x}-${y}`} />)
    }
  }
  return <div className="board" style={{ gridTemplateColumns: `repeat(${game.width}, 1fr)` }}>{cells}</div>
}

function Controls({ disabled, onMove }) {
  return <div className={`controls ${disabled ? 'disabled' : ''}`}>
    <button onClick={() => onMove('UP')}>↑</button>
    <button onClick={() => onMove('LEFT')}>←</button>
    <button onClick={() => onMove('DOWN')}>↓</button>
    <button onClick={() => onMove('RIGHT')}>→</button>
  </div>
}
