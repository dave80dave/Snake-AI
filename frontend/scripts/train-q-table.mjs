import { writeFileSync } from 'node:fs'

const EPISODES = 100_000
const MAX_STEPS = 500
const actions = ['STRAIGHT', 'TURN_LEFT', 'TURN_RIGHT']
const vectors = { UP: [0, -1], DOWN: [0, 1], LEFT: [-1, 0], RIGHT: [1, 0] }
const left = { UP: 'LEFT', LEFT: 'DOWN', DOWN: 'RIGHT', RIGHT: 'UP' }
const right = { UP: 'RIGHT', RIGHT: 'DOWN', DOWN: 'LEFT', LEFT: 'UP' }
const qTable = {}

function createGame() {
  const game = { width: 20, height: 20, snake: [{ x: 10, y: 10 }], direction: 'RIGHT', score: 0, gameOver: false }
  placeFood(game)
  return game
}

function placeFood(game) {
  do game.food = { x: Math.floor(Math.random() * game.width), y: Math.floor(Math.random() * game.height) }
  while (game.snake.some(part => part.x === game.food.x && part.y === game.food.y))
}

function directionFor(direction, action) {
  if (action === 'TURN_LEFT') return left[direction]
  if (action === 'TURN_RIGHT') return right[direction]
  return direction
}

function danger(game, direction) {
  const [dx, dy] = vectors[direction]
  const head = { x: game.snake[0].x + dx, y: game.snake[0].y + dy }
  const grows = head.x === game.food.x && head.y === game.food.y
  const body = grows ? game.snake : game.snake.slice(0, -1)
  return head.x < 0 || head.x >= game.width || head.y < 0 || head.y >= game.height
    || body.some(part => part.x === head.x && part.y === head.y)
}

function state(game) {
  const head = game.snake[0]
  return [
    game.direction, left[game.direction], right[game.direction],
  ].map(direction => Number(danger(game, direction))).concat([
    Number(game.food.y < head.y), Number(game.food.y > head.y),
    Number(game.food.x < head.x), Number(game.food.x > head.x),
  ]).join('')
}

function move(game, direction) {
  const [dx, dy] = vectors[direction]
  const head = { x: game.snake[0].x + dx, y: game.snake[0].y + dy }
  const grows = head.x === game.food.x && head.y === game.food.y
  const body = grows ? game.snake : game.snake.slice(0, -1)
  if (head.x < 0 || head.x >= game.width || head.y < 0 || head.y >= game.height
    || body.some(part => part.x === head.x && part.y === head.y)) {
    game.gameOver = true
    return
  }
  game.snake = [head, ...body]
  game.direction = direction
  if (grows) { game.score++; placeFood(game) }
}

let totalScore = 0
let bestScore = 0
for (let episode = 1; episode <= EPISODES; episode++) {
  const game = createGame()
  const epsilon = Math.max(0.02, 0.35 * (1 - episode / EPISODES))
  for (let step = 0; step < MAX_STEPS && !game.gameOver; step++) {
    const oldState = state(game)
    const oldDistance = Math.abs(game.food.x - game.snake[0].x) + Math.abs(game.food.y - game.snake[0].y)
    const values = qTable[oldState] ?? [0, 0, 0]
    const max = Math.max(...values)
    const best = values.map((value, index) => value === max ? index : -1).filter(index => index >= 0)
    const actionIndex = Math.random() < epsilon
      ? Math.floor(Math.random() * actions.length)
      : best[Math.floor(Math.random() * best.length)]
    const oldScore = game.score
    move(game, directionFor(game.direction, actions[actionIndex]))
    const newDistance = Math.abs(game.food.x - game.snake[0].x) + Math.abs(game.food.y - game.snake[0].y)
    const reward = game.gameOver ? -100
      : game.score > oldScore ? 50
        : newDistance < oldDistance ? 1
          : newDistance > oldDistance ? -1 : -0.1
    const future = qTable[state(game)] ?? [0, 0, 0]
    const target = reward + (game.gameOver ? 0 : 0.9 * Math.max(...future))
    values[actionIndex] += 0.15 * (target - values[actionIndex])
    qTable[oldState] = values
  }
  totalScore += game.score
  bestScore = Math.max(bestScore, game.score)
}

let evaluationTotal = 0
let evaluationBest = 0
const evaluationEpisodes = 10_000
for (let episode = 0; episode < evaluationEpisodes; episode++) {
  const game = createGame()
  for (let step = 0; step < MAX_STEPS && !game.gameOver; step++) {
    const values = qTable[state(game)] ?? [0, 0, 0]
    const max = Math.max(...values)
    const bestIndexes = values.map((value, index) => value === max ? index : -1).filter(index => index >= 0)
    const bestIndex = bestIndexes[Math.floor(Math.random() * bestIndexes.length)]
    move(game, directionFor(game.direction, actions[bestIndex]))
  }
  evaluationTotal += game.score
  evaluationBest = Math.max(evaluationBest, game.score)
}

writeFileSync(new URL('../src/pretrained-q-table.json', import.meta.url), `${JSON.stringify(qTable, null, 2)}\n`)
console.log(JSON.stringify({
  episodes: EPISODES,
  states: Object.keys(qTable).length,
  trainingAverage: totalScore / EPISODES,
  trainingBest: bestScore,
  evaluationEpisodes,
  evaluationAverage: evaluationTotal / evaluationEpisodes,
  evaluationBest,
}, null, 2))
