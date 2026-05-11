import request from './request'

export function getIncomingFiles(params) { return request.get('/incoming-file', { params }) }
export function createIncomingFile(data) { return request.post('/incoming-file', data) }
export function updateIncomingFile(id, data) { return request.put(`/incoming-file/${id}`, data) }
export function deleteIncomingFile(id) { return request.delete(`/incoming-file/${id}`) }

export function getOutgoingFiles(params) { return request.get('/outgoing-file', { params }) }
export function createOutgoingFile(data) { return request.post('/outgoing-file', data) }
export function updateOutgoingFile(id, data) { return request.put(`/outgoing-file/${id}`, data) }
export function deleteOutgoingFile(id) { return request.delete(`/outgoing-file/${id}`) }

export function getFileArchives(params) { return request.get('/file-archive', { params }) }
export function createFileArchive(data) { return request.post('/file-archive', data) }
export function updateFileArchive(id, data) { return request.put(`/file-archive/${id}`, data) }
export function deleteFileArchive(id) { return request.delete(`/file-archive/${id}`) }
