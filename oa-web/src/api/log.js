import request from './request'

export function getWorkLogs(params) { return request.get('/work-log', { params }) }
export function createWorkLog(data) { return request.post('/work-log', data) }
export function updateWorkLog(id, data) { return request.put(`/work-log/${id}`, data) }
