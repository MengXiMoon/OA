import request from './request'

export function generateWeeklySummary(logs) {
  return request.post('/ai/weekly-summary', { logs })
}

export function suggestTaskPriority(tasks) {
  return request.post('/ai/task-suggest', { tasks })
}
