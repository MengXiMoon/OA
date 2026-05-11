<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>OA办公自动化系统</h2>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" style="width:100%">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="showRegister = true" style="width:100%">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog v-model="showRegister" title="用户注册" width="400px">
      <el-form :model="regForm">
        <el-form-item label="用户名"><el-input v-model="regForm.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="regForm.password" type="password" /></el-form-item>
        <el-form-item label="真实姓名"><el-input v-model="regForm.realName" /></el-form-item>
        <el-form-item><el-button type="primary" @click="handleRegister">注册</el-button></el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const loading = ref(false)
const showRegister = ref(false)
const regForm = reactive({ username: '', password: '', realName: '' })

const rules = {
  username: [{ required: true, message: '请输入用户名' }],
  password: [{ required: true, message: '请输入密码' }]
}

async function handleLogin() {
  loading.value = true
  try {
    await userStore.doLogin(form.username, form.password)
    router.push('/')
    ElMessage.success('登录成功')
  } finally {
    loading.value = false
  }
}

async function handleRegister() {
  await userStore.doRegister(regForm.username, regForm.password, regForm.realName)
  showRegister.value = false
  router.push('/')
  ElMessage.success('注册成功')
}
</script>

<style scoped>
.login-container {
  display: flex; justify-content: center; align-items: center;
  height: 100vh; background: #f5f5f5;
}
.login-card { width: 400px; }
.login-card h2 { text-align: center; margin-bottom: 20px; }
</style>
