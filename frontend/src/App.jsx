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

function safeLocalDirection(game) {
  const candidates = Object.keys(vectors).filter(direction => opposite[game.direction] !== direction)
  const safe = candidates.filter(direction => !localMove(game, direction).gameOver)
  const choices = safe.length ? safe : candidates
  return choices[Math.floor(Math.random() * choices.length)]
}

async function request(path, options) {
  const response = await fetch(`${apiBase}${path}`, options)
  if (!response.ok) throw new Error('Das Backend ist nicht erreichbar.')
  return response.json()
}

export default function App() {
  const [game, setGame] = useState(null)
  const [aiMode, setAiMode] = useState(false)
  const [error, setError] = useState('')
  const [localMode, setLocalMode] = useState(false)
  const busy = useRef(false)
  const aiModeRef = useRef(false)

  const setMode = (enabled) => {
    aiModeRef.current = enabled
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
        : localMove(current, path.endsWith('/ai-step') ? safeLocalDirection(current) : body.direction))
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
      action('/api/game/move', { direction })
    }
    window.addEventListener('keydown', onKeyDown)
    return () => window.removeEventListener('keydown', onKeyDown)
  }, [action, aiMode])

  useEffect(() => {
    if (!aiMode || game?.gameOver) return
    const timer = setInterval(() => {
      if (aiModeRef.current) action('/api/game/ai-step')
    }, 135)
    return () => clearInterval(timer)
  }, [action, aiMode, game?.gameOver])

  const restart = async () => {
    setMode(false)
    await action('/api/game/new')
  }

  return (
    <main className="page-shell">
      <header>
        <div className="brand-mark">S</div>
        <div><p className="eyebrow">JAVA × REACT</p><h1>Snake Lab</h1></div>
        <div className={`connection ${error ? 'offline' : ''}`}>
          <span />{error ? 'Offline' : localMode ? 'Browser-Modus' : 'Backend verbunden'}
        </div>
      </header>

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
            <p>{aiMode ? 'Der sichere Zufalls-Agent steuert.' : 'Ein Tastendruck entspricht einem Schritt.'}</p>
          </div>
          <Controls disabled={aiMode} onMove={(direction) => action('/api/game/move', { direction })} />
          <button className="restart" onClick={restart}>↻ Neues Spiel</button>
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
