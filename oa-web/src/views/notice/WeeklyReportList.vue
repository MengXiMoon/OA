<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="keyword" placeholder="搜索标题" class="search-input" clearable @change="fetchData" />
        <el-button v-if="userStore.role==='admin'" type="primary" @click="openDialog()">发布周报</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="reportWeek" label="报告周" width="140" />
        <el-table-column label="发布时间" width="170">
          <template #default="{row}">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{row}">
            <el-button size="small" @click="showDetail(row)">查看</el-button>
            <template v-if="userStore.role==='admin'">
              <el-button size="small" @click="openDialog(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑周报':'发布周报'" width="600px">
      <el-form :model="form">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="6" /></el-form-item>
        <el-form-item label="报告周"><el-input v-model="form.reportWeek" placeholder="如：2026-W19" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="周报详情" width="600px">
      <h2>{{ detail.title }}</h2>
      <p style="color:#999">报告周：{{ detail.reportWeek }}</p>
      <el-divider />
      <div>{{ detail.content }}</div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getWeeklyReports, createWeeklyReport, updateWeeklyReport, deleteWeeklyReport } from '@/api/notice'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const records = ref([])
const total = ref(0)
const page = ref(1)
const keyword = ref('')
const dialogVisible = ref(false)
const detailVisible = ref(false)
const isEdit = ref(false)
const detail = ref({})
const form = reactive({ title: '', content: '', reportWeek: '' })
let editId = null

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }
async function fetchData() {
  const res = await getWeeklyReports({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.title = row.title; form.content = row.content; form.reportWeek = row.reportWeek
  } else {
    isEdit.value = false; editId = null
    form.title = ''; form.content = ''; form.reportWeek = ''
  }
  dialogVisible.value = true
}

async function handleSave() {
  if (isEdit.value) { await updateWeeklyReport(editId, form) }
  else { await createWeeklyReport(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '发布成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteWeeklyReport(id)
  ElMessage.success('已删除')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
