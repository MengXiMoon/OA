<template>
  <div>
    <el-card>
      <div class="toolbar">
        <div>
          <el-date-picker v-model="filterDate" type="date" value-format="YYYY-MM-DD" placeholder="按日期筛选" @change="fetchData" style="width:200px;margin-right:10px" />
          <el-input v-model="keyword" placeholder="搜索内容" style="width:200px" clearable @change="fetchData" />
        </div>
        <el-button type="primary" @click="openDialog()">写日志</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column label="日期" width="170">
          <template #default="{row}">{{ formatTime(row.logDate) }}</template>
        </el-table-column>
        <el-table-column prop="todayContent" label="今日工作" show-overflow-tooltip />
        <el-table-column prop="tomorrowPlan" label="明日计划" show-overflow-tooltip />
        <el-table-column label="操作" width="220">
          <template #default="{row}">
            <el-button size="small" @click="openDialog(row)">编辑</el-button>
            <el-button size="small" @click="showDetail(row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑日志':'写日志'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="日期"><el-date-picker v-model="form.logDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="今日工作"><el-input v-model="form.todayContent" type="textarea" :rows="5" /></el-form-item>
        <el-form-item label="明日计划"><el-input v-model="form.tomorrowPlan" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="日志详情" width="600px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="日期">{{ detail.logDate }}</el-descriptions-item>
        <el-descriptions-item label="今日工作">{{ detail.todayContent }}</el-descriptions-item>
        <el-descriptions-item label="明日计划">{{ detail.tomorrowPlan }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { getWorkLogs, createWorkLog, updateWorkLog } from '@/api/log'
import { ElMessage } from 'element-plus'

const records = ref([])
const total = ref(0)
const page = ref(1)
const keyword = ref('')
const filterDate = ref('')
const dialogVisible = ref(false)
const detailVisible = ref(false)
const isEdit = ref(false)
const detail = ref({})
const formRef = ref(null)
const form = reactive({ logDate: '', todayContent: '', tomorrowPlan: '' })
let editId = null

const rules = {
  todayContent: [{ required: true, message: '请输入今日工作内容', trigger: 'blur' }],
}

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }

async function fetchData() {
  const res = await getWorkLogs({ page: page.value, pageSize: 10, keyword: keyword.value, date: filterDate.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.logDate = row.logDate; form.todayContent = row.todayContent; form.tomorrowPlan = row.tomorrowPlan
  } else {
    isEdit.value = false; editId = null
    form.logDate = ''; form.todayContent = ''; form.tomorrowPlan = ''
  }
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) { await updateWorkLog(editId, form) }
  else { await createWorkLog(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '保存成功')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
