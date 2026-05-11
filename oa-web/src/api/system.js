import request from './request'

export function getUsers(params) { return request.get('/system/user', { params }) }
export function updateUser(id, data) { return request.put(`/system/user/${id}`, data) }

export function getDepartments() { return request.get('/system/department') }
export function createDepartment(data) { return request.post('/system/department', data) }
export function updateDepartment(id, data) { return request.put(`/system/department/${id}`, data) }
export function deleteDepartment(id) { return request.delete(`/system/department/${id}`) }
