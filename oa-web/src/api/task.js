import request from './request'

export function getMyTasks(params) { return request.get('/task/my', { params }) }
export function getAssignedTasks(params) { return request.get('/task/assigned', { params }) }
export function createTask(data) { return request.post('/task', data) }
export function updateTask(id, data) { return request.put(`/task/${id}`, data) }
