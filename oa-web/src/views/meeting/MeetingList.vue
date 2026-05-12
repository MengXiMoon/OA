<template>
  <div>
    <el-card>
      <div class="toolbar">
        <div>
          <el-input v-model="keyword" placeholder="搜索标题" class="search-input" clearable @change="fetchData" />
        </div>
        <el-button v-if="userStore.role==='admin' || userStore.role==='manager'" type="primary" @click="openDialog()">预约会议</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="roomId" label="会议室ID" width="100" />
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
            <template v-if="userStore.role==='admin' || userStore.role==='manager'">
              <el-button size="small" @click="openDialog(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(row.id)">取消</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑会议':'预约会议'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="标题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="会议室ID" prop="roomId"><el-input-number v-model="form.roomId" :min="1" /></el-form-item>
        <el-form-item label="开始时间" prop="startTime"><el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item>
        <el-form-item label="结束时间" prop="endTime"><el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="预约中" value="预约中" />
            <el-option label="已开始" value="已开始" />
            <el-option label="已结束" value="已结束" />
            <el-option label="已取消" value="已取消" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="会议详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="标题" :span="2">{{ detail.title }}</el-descriptions-item>
        <el-descriptions-item label="会议室ID">{{ detail.roomId }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ detail.status }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ detail.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ detail.endTime }}</el-descriptions-item>
        <el-descriptions-item label="内容" :span="2">{{ detail.content }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getMeetings, createMeeting, updateMeeting, deleteMeeting } from '@/api/meeting'
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
const formRef = ref(null)
const form = reactive({ title: '', roomId: 1, startTime: '', endTime: '', content: '', status: '预约中' })
let editId = null

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  roomId: [{ required: true, message: '请选择会议室', trigger: 'change' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }],
}

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }

async function fetchData() {
  const res = await getMeetings({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.title = row.title; form.roomId = row.roomId; form.startTime = row.startTime
    form.endTime = row.endTime; form.content = row.content; form.status = row.status
  } else {
    isEdit.value = false; editId = null
    form.title = ''; form.roomId = 1; form.startTime = ''; form.endTime = ''; form.content = ''; form.status = '预约中'
  }
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) { await updateMeeting(editId, form) }
  else { await createMeeting(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '预约成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定取消？', '提示', { type: 'warning' })
  await deleteMeeting(id)
  ElMessage.success('已取消')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
