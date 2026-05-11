import request from './request'

export function getMeetingRooms() { return request.get('/meeting-room') }
export function createMeetingRoom(data) { return request.post('/meeting-room', data) }
export function updateMeetingRoom(id, data) { return request.put(`/meeting-room/${id}`, data) }
export function deleteMeetingRoom(id) { return request.delete(`/meeting-room/${id}`) }

export function getMeetings(params) { return request.get('/meeting', { params }) }
export function createMeeting(data) { return request.post('/meeting', data) }
export function updateMeeting(id, data) { return request.put(`/meeting/${id}`, data) }
export function deleteMeeting(id) { return request.delete(`/meeting/${id}`) }
