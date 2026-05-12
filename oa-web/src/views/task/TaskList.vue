<template>
  <div>
    <el-card>
      <el-tabs v-model="activeTab" @tab-change="fetchData">
        <el-tab-pane label="我的任务" name="my" />
        <el-tab-pane v-if="userStore.role==='admin' || userStore.role==='manager'" label="分配的任务" name="assigned" />
      </el-tabs>
      <div class="toolbar">
        <div>
          <el-input v-model="keyword" placeholder="搜索标题" class="search-input" clearable @change="fetchData" />
        </div>
        <el-button v-if="userStore.role==='admin' || userStore.role==='manager'" type="primary" @click="openDialog()">创建任务</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column label="截止日期" width="170">
          <template #default="{row}">{{ formatTime(row.deadline) }}</template>
        </el-table-column>
        <el-table-column prop="priority" label="优先级" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="220">
          <template #default="{row}">
            <el-button size="small" @click="showDetail(row)">查看</el-button>
            <template v-if="userStore.role==='admin' || userStore.role==='manager'">
              <el-button size="small" @click="openDialog(row)">编辑</el-button>
              <el-button size="small" @click="handleComplete(row)">完成</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑任务':'创建任务'" width="600px">
      <el-form :model="form">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="截止日期"><el-date-picker v-model="form.deadline" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="优先级">
          <el-select v-model="form.priority">
            <el-option label="高" value="高" />
            <el-option label="中" value="中" />
            <el-option label="低" value="低" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="待处理" value="待处理" />
            <el-option label="进行中" value="进行中" />
            <el-option label="已完成" value="已完成" />
          </el-select>
        </el-form-item>
        <el-form-item label="分配给"><el-input v-model="form.assigneeId" placeholder="用户ID" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="任务详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="标题" :span="2">{{ detail.title }}</el-descriptions-item>
        <el-descriptions-item label="内容" :span="2">{{ detail.content }}</el-descriptions-item>
        <el-descriptions-item label="截止日期">{{ detail.deadline }}</el-descriptions-item>
        <el-descriptions-item label="优先级">{{ detail.priority }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ detail.status }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getMyTasks, getAssignedTasks, createTask, updateTask } from '@/api/task'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const activeTab = ref('my')
const records = ref([])
const total = ref(0)
const page = ref(1)
const keyword = ref('')
const dialogVisible = ref(false)
const detailVisible = ref(false)
const isEdit = ref(false)
const detail = ref({})
const form = reactive({ title: '', content: '', deadline: '', priority: '中', status: '待处理', assigneeId: '' })
let editId = null

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }

async function fetchData() {
  const fn = activeTab.value === 'my' ? getMyTasks : getAssignedTasks
  const res = await fn({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.title = row.title; form.content = row.content; form.deadline = row.deadline
    form.priority = row.priority; form.status = row.status; form.assigneeId = row.assigneeId || ''
  } else {
    isEdit.value = false; editId = null
    form.title = ''; form.content = ''; form.deadline = ''; form.priority = '中'; form.status = '待处理'; form.assigneeId = ''
  }
  dialogVisible.value = true
}

async function handleSave() {
  if (isEdit.value) { await updateTask(editId, form) }
  else { await createTask(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '创建成功')
  fetchData()
}

async function handleComplete(row) {
  await ElMessageBox.confirm('确定标记为已完成？', '提示', { type: 'warning' })
  await updateTask(row.id, { status: '已完成' })
  ElMessage.success('已完成')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
