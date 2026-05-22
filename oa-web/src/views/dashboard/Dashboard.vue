<template>
  <div class="dashboard">
    <div class="greeting">
      <h1 class="greeting-text">{{ greeting }}，{{ userStore.userInfo?.realName }}</h1>
      <p class="greeting-date">{{ today }}</p>
    </div>
    <div class="stat-grid">
      <div class="stat-card" v-for="item in stats" :key="item.label">
        <div class="stat-icon"><el-icon :size="14"><component :is="item.icon" /></el-icon></div>
        <div class="stat-body">
          <span class="stat-number">{{ item.value }}</span>
          <span class="stat-label">{{ item.label }}</span>
        </div>
      </div>
    </div>

    <!-- AI 助手 -->
    <div class="ai-card">
      <div class="ai-header">
        <span class="ai-title">AI 助手</span>
        <span class="ai-badge">DeepSeek</span>
      </div>
      <div class="ai-actions">
        <el-button @click="generateSummary" :loading="aiLoading">
          <el-icon><MagicStick /></el-icon> 生成周报摘要
        </el-button>
        <el-button @click="suggestTasks" :loading="aiLoading">
          <el-icon><Cpu /></el-icon> 任务优先级建议
        </el-button>
      </div>
      <div v-if="aiResult" class="ai-result">
        <div class="ai-result-header">
          <el-icon :size="14"><ChatDotRound /></el-icon>
          <span>AI 回复</span>
        </div>
        <p class="ai-result-text">{{ aiResult }}</p>
      </div>
    </div>

    <div class="welcome-card">
      <h3 class="welcome-title">工作台</h3>
      <p class="welcome-text">当前角色：{{ roleName }}。从这里开始你的一天，所有模块均可从左侧导航栏访问。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { Bell, List, DocumentChecked, Calendar, MagicStick, Cpu, ChatDotRound } from '@element-plus/icons-vue'
import { generateWeeklySummary, suggestTaskPriority } from '@/api/ai'
import { getMyTasks } from '@/api/task'
import { getWorkLogs } from '@/api/log'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工', '管理员': '管理员', '部门经理': '部门经理', '普通员工': '普通员工' }
  return map[userStore.role] || ''
})

const greeting = computed(() => {
  const h = new Date().getHours()
  if (h < 12) return '早上好'
  if (h < 18) return '下午好'
  return '晚上好'
})

const today = computed(() => new Date().toLocaleDateString('zh-CN', { year:'numeric', month:'long', day:'numeric', weekday:'long' }))

const stats = ref([
  { label: '公告', value: 12, icon: Bell },
  { label: '任务', value: 5, icon: List },
  { label: '待审批', value: 3, icon: DocumentChecked },
  { label: '会议', value: 5, icon: Calendar },
])

const aiLoading = ref(false)
const aiResult = ref('')

async function generateSummary() {
  aiLoading.value = true
  aiResult.value = ''
  try {
    const logs = await getWorkLogs({ page: 1, pageSize: 20 })
    const logTexts = (logs.data?.records || []).map(l => l.todayContent || l.content || '').filter(t => t)
    if (logTexts.length === 0) {
      aiResult.value = '暂无工作日志，请先到工作日志模块填写。'
    } else {
      const res = await generateWeeklySummary(logTexts)
      aiResult.value = res.data
    }
  } catch {
    ElMessage.warning('AI 服务未配置或调用失败')
  } finally {
    aiLoading.value = false
  }
}

async function suggestTasks() {
  aiLoading.value = true
  aiResult.value = ''
  try {
    const tasks = await getMyTasks({ page: 1, pageSize: 20 })
    const taskTexts = (tasks.data?.records || []).map(t => `【${t.title}】优先级:${t.priority || '中'} 截止:${t.deadline || '未设定'} 状态:${t.status || '未知'}`)
    if (taskTexts.length === 0) {
      aiResult.value = '暂无待处理任务。'
    } else {
      const res = await suggestTaskPriority(taskTexts)
      aiResult.value = res.data
    }
  } catch {
    ElMessage.warning('AI 服务未配置或调用失败')
  } finally {
    aiLoading.value = false
  }
}
</script>

<style scoped>
.dashboard { max-width: 720px; }
.greeting { margin-bottom: 24px; }
.greeting-text { font-size: 20px; font-weight: 500; color: var(--text-primary); margin: 0 0 4px; }
.greeting-date { font-size: 12px; color: var(--text-disabled); margin: 0; }
.stat-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 12px; margin-bottom: 20px; }
.stat-card {
  display: flex; align-items: flex-start; gap: 10px; padding: 16px;
  background: var(--bg-surface); border: 0.5px solid var(--border-color);
  border-radius: var(--radius-sm); cursor: default;
  transition: border-color 0.15s, background 0.15s;
}
.stat-card:hover { border-color: var(--text-primary); background: rgba(0,0,0,0.005); }
.stat-icon { display: flex; align-items: center; justify-content: center; width: 28px; height: 28px; border-radius: var(--radius-sm); background: rgba(0,0,0,0.04); color: var(--text-primary); flex-shrink: 0; }
.stat-body { display: flex; flex-direction: column; }
.stat-number { font-size: 22px; font-weight: 500; color: var(--text-primary); line-height: 1.1; }
.stat-label { font-size: 12px; color: var(--text-secondary); margin-top: 2px; }

.ai-card {
  padding: 20px; margin-bottom: 20px;
  background: var(--bg-surface); border: 0.5px solid var(--border-color);
  border-radius: var(--radius-sm);
}
.ai-header { display: flex; align-items: center; gap: 8px; margin-bottom: 14px; }
.ai-title { font-size: 14px; font-weight: 500; color: var(--text-primary); }
.ai-badge { font-size: 10px; color: var(--text-disabled); background: rgba(0,0,0,0.04); padding: 1px 6px; border-radius: 100px; }
.ai-actions { display: flex; gap: 10px; margin-bottom: 14px; }
.ai-result { padding: 14px; background: rgba(0,0,0,0.02); border-radius: var(--radius-sm); border: 0.5px solid var(--border-color); }
.ai-result-header { display: flex; align-items: center; gap: 6px; margin-bottom: 8px; font-size: 12px; color: var(--text-secondary); }
.ai-result-text { font-size: 13px; color: var(--text-primary); line-height: 1.7; white-space: pre-wrap; margin: 0; }

.welcome-card { padding: 20px; background: var(--bg-surface); border: 0.5px solid var(--border-color); border-radius: var(--radius-sm); }
.welcome-title { font-size: 14px; font-weight: 500; color: var(--text-primary); margin: 0 0 6px; }
.welcome-text { font-size: 13px; color: var(--text-secondary); margin: 0; line-height: 1.6; }
@media (max-width: 900px) { .stat-grid { grid-template-columns: repeat(2, 1fr); } }
</style>
