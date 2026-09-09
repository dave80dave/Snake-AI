import { useCallback, useEffect, useRef, useState } from 'react'

const directions = {
  ArrowUp: 'UP', w: 'UP', W: 'UP',
  ArrowDown: 'DOWN', s: 'DOWN', S: 'DOWN',
  ArrowLeft: 'LEFT', a: 'LEFT', A: 'LEFT',
  ArrowRight: 'RIGHT', d: 'RIGHT', D: 'RIGHT',
}

const apiBase = import.meta.env.VITE_API_URL ?? ''

async function request(path, options) {
  const response = await fetch(`${apiBase}${path}`, options)
  if (!response.ok) throw new Error('Das Backend ist nicht erreichbar.')
  return response.json()
}

export default function App() {
  const [game, setGame] = useState(null)
  const [aiMode, setAiMode] = useState(false)
  const [error, setError] = useState('')
  const busy = useRef(false)

  const load = useCallback(async () => {
    try {
      setGame(await request('/api/game'))
      setError('')
    } catch (err) { setError(err.message) }
  }, [])

  const action = useCallback(async (path, body) => {
    if (busy.current) return
    busy.current = true
    try {
      setGame(await request(path, {
        method: 'POST',
        headers: body ? { 'Content-Type': 'application/json' } : undefined,
        body: body ? JSON.stringify(body) : undefined,
      }))
      setError('')
    } catch (err) { setError(err.message) }
    finally { busy.current = false }
  }, [])

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
    const timer = setInterval(() => action('/api/game/ai-step'), 135)
    return () => clearInterval(timer)
  }, [action, aiMode, game?.gameOver])

  const restart = async () => {
    setAiMode(false)
    await action('/api/game/new')
  }

  return (
    <main className="page-shell">
      <header>
        <div className="brand-mark">S</div>
        <div><p className="eyebrow">JAVA × REACT</p><h1>Snake Lab</h1></div>
        <div className={`connection ${error ? 'offline' : ''}`}>
          <span />{error ? 'Offline' : 'Backend verbunden'}
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
              <button className={!aiMode ? 'active' : ''} onClick={() => setAiMode(false)}>DU</button>
              <button className={aiMode ? 'active' : ''} onClick={() => setAiMode(true)}>KI</button>
            </div>
            <p>{aiMode ? 'Der sichere Zufalls-Agent steuert.' : 'Pfeiltasten oder WASD verwenden.'}</p>
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
