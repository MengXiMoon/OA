import request from './request'

export function login(username, password) {
  return request.post('/auth/login', { username, password })
}

export function register(username, password, realName) {
  return request.post('/auth/register', { username, password, realName })
}

export function getUserInfo() {
  return request.get('/auth/userinfo')
}
