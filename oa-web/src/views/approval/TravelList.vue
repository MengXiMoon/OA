<template>
  <div>
    <el-card>
      <div class="toolbar">
        <div>
          <el-input v-model="keyword" placeholder="搜索" class="search-input" clearable @change="fetchData" />
        </div>
        <el-button type="primary" @click="openDialog()">申请出差</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="destination" label="目的地" width="150" />
        <el-table-column prop="reason" label="原因" show-overflow-tooltip />
        <el-table-column label="开始时间" width="170">
          <template #default="{row}">{{ formatTime(row.startTime) }}</template>
        </el-table-column>
        <el-table-column label="结束时间" width="170">
          <template #default="{row}">{{ formatTime(row.endTime) }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="220">
          <template #default="{row}">
            <el-button size="small" @click="showDetail(row)">查看</el-button>
            <template v-if="userStore.role==='管理员' || userStore.role==='部门经理'">
              <el-button v-if="row.status==='待审批'" size="small" type="success" @click="handleApprove(row.id, '已批准')">批准</el-button>
              <el-button v-if="row.status==='待审批'" size="small" type="danger" @click="handleApprove(row.id, '已拒绝')">拒绝</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="申请出差" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="目的地" prop="destination"><el-input v-model="form.destination" /></el-form-item>
        <el-form-item label="原因" prop="reason"><el-input v-model="form.reason" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="开始时间" prop="startTime"><el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" /></el-form-item>
        <el-form-item label="结束时间" prop="endTime"><el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">提交</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="出差详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="目的地">{{ detail.destination }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ detail.status }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ detail.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ detail.endTime }}</el-descriptions-item>
        <el-descriptions-item label="原因" :span="2">{{ detail.reason }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getTravels, applyTravel, approveTravel } from '@/api/approval'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const records = ref([])
const total = ref(0)
const page = ref(1)
const keyword = ref('')
const dialogVisible = ref(false)
const detailVisible = ref(false)
const detail = ref({})
const formRef = ref(null)
const form = reactive({ destination: '', reason: '', startTime: '', endTime: '' })

const rules = {
  destination: [{ required: true, message: '请输入目的地', trigger: 'blur' }],
  reason: [{ required: true, message: '请输入原因', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }],
}

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }

async function fetchData() {
  const res = await getTravels({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog() {
  form.destination = ''; form.reason = ''; form.startTime = ''; form.endTime = ''
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  await applyTravel(form)
  dialogVisible.value = false
  ElMessage.success('申请已提交')
  fetchData()
}

async function handleApprove(id, status) {
  const label = status === '已批准' ? '批准' : '拒绝'
  await ElMessageBox.confirm(`确定${label}？`, '提示', { type: 'warning' })
  await approveTravel(id, status)
  ElMessage.success(`已${label}`)
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
