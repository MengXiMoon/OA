import request from './request'

export function getNotices(params) { return request.get('/notice', { params }) }
export function getNotice(id) { return request.get(`/notice/${id}`) }
export function createNotice(data) { return request.post('/notice', data) }
export function updateNotice(id, data) { return request.put(`/notice/${id}`, data) }
export function deleteNotice(id) { return request.delete(`/notice/${id}`) }

export function getCompanyEvents(params) { return request.get('/company-event', { params }) }
export function createCompanyEvent(data) { return request.post('/company-event', data) }
export function updateCompanyEvent(id, data) { return request.put(`/company-event/${id}`, data) }
export function deleteCompanyEvent(id) { return request.delete(`/company-event/${id}`) }

export function getProjectProgresses(params) { return request.get('/project-progress', { params }) }
export function createProjectProgress(data) { return request.post('/project-progress', data) }
export function updateProjectProgress(id, data) { return request.put(`/project-progress/${id}`, data) }
export function deleteProjectProgress(id) { return request.delete(`/project-progress/${id}`) }

export function getWeeklyReports(params) { return request.get('/weekly-report', { params }) }
export function createWeeklyReport(data) { return request.post('/weekly-report', data) }
export function updateWeeklyReport(id, data) { return request.put(`/weekly-report/${id}`, data) }
export function deleteWeeklyReport(id) { return request.delete(`/weekly-report/${id}`) }
