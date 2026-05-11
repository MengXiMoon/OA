import request from './request'

export function getLeaves(params) { return request.get('/leave', { params }) }
export function applyLeave(data) { return request.post('/leave', data) }
export function approveLeave(id, status) { return request.put(`/leave/${id}/approve`, { status }) }

export function getTravels(params) { return request.get('/travel', { params }) }
export function applyTravel(data) { return request.post('/travel', data) }
export function approveTravel(id, status) { return request.put(`/travel/${id}/approve`, { status }) }

export function getAttendances(params) { return request.get('/attendance', { params }) }
export function getAllAttendances(params) { return request.get('/attendance/all', { params }) }
