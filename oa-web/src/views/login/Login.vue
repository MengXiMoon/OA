<template>
  <div class="login">
    <div class="login-panel">
      <div class="login-header">
        <h1 class="login-brand">OA</h1>
        <p class="login-desc">办公自动化系统</p>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" :prefix-icon="User" @keyup.enter="focusPassword" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input ref="pwdRef" v-model="form.password" type="password" placeholder="密码" :prefix-icon="Lock" show-password @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-btn" :loading="loading" @click="handleLogin">登 录</el-button>
        </el-form-item>
      </el-form>
      <div class="login-divider"><span>或</span></div>
      <el-button class="login-register" @click="showRegister = true">注册新账号</el-button>
    </div>
    <el-dialog v-model="showRegister" title="注册" width="400px" align-center>
      <el-form :model="regForm" label-position="top">
        <el-form-item label="用户名"><el-input v-model="regForm.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="regForm.password" type="password" /></el-form-item>
        <el-form-item label="真实姓名"><el-input v-model="regForm.realName" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRegister = false">取消</el-button>
        <el-button type="primary" @click="handleRegister">注册</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref(null)
const pwdRef = ref(null)
const form = reactive({ username: 'admin', password: '123456' })
const loading = ref(false)
const showRegister = ref(false)
const regForm = reactive({ username: '', password: '', realName: '' })

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

function focusPassword() { pwdRef.value?.focus() }

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  loading.value = true
  try {
    await userStore.doLogin(form.username, form.password)
    ElMessage({ message: '登录成功', type: 'success', duration: 1500 })
    setTimeout(() => router.push('/'), 200)
  } finally { loading.value = false }
}

async function handleRegister() {
  await userStore.doRegister(regForm.username, regForm.password, regForm.realName)
  showRegister.value = false
  ElMessage({ message: '注册成功', type: 'success', duration: 1500 })
  setTimeout(() => router.push('/'), 200)
}
</script>

<style scoped>
.login { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: var(--bg-primary); }
.login-panel { width: 320px; }
.login-header { text-align: center; margin-bottom: 28px; }
.login-brand { font-size: 28px; font-weight: 600; color: var(--text-primary); margin: 0 0 4px; letter-spacing: -0.01em; }
.login-desc { font-size: 12px; color: var(--text-disabled); margin: 0; }
.login-form { margin-bottom: 18px; }
.login-form :deep(.el-form-item) { margin-bottom: 12px; }
.login-btn { width: 100%; height: 36px; font-size: 14px; letter-spacing: 0.06em; }
.login-divider { display: flex; align-items: center; gap: 12px; margin-bottom: 18px; }
.login-divider::before, .login-divider::after { content: ''; flex: 1; height: 0.5px; background: var(--border-color); }
.login-divider span { font-size: 12px; color: var(--text-disabled); }
.login-register { width: 100%; height: 36px; font-size: 13px; }
</style>
