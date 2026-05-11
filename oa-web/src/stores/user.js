import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, register, getUserInfo } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(null)
  const role = ref('')

  async function doLogin(username, password) {
    const res = await login(username, password)
    localStorage.setItem('oa_token', res.data.token)
    userInfo.value = res.data
    role.value = res.data.role
    return res
  }

  async function doRegister(username, password, realName) {
    const res = await register(username, password, realName)
    localStorage.setItem('oa_token', res.data.token)
    userInfo.value = res.data
    role.value = res.data.role
    return res
  }

  async function fetchUserInfo() {
    const res = await getUserInfo()
    userInfo.value = res.data
    role.value = res.data.role
  }

  function logout() {
    localStorage.removeItem('oa_token')
    userInfo.value = null
    role.value = ''
  }

  return { userInfo, role, doLogin, doRegister, fetchUserInfo, logout }
})
