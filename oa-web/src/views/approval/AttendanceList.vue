<template>
  <div>
    <el-card>
      <el-tabs v-if="userStore.role==='管理员'" v-model="activeTab" @tab-change="fetchData">
        <el-tab-pane label="我的考勤" name="my" />
        <el-tab-pane label="全部考勤" name="all" />
      </el-tabs>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column label="日期" width="170">
          <template #default="{row}">{{ formatTime(row.recordDate) }}</template>
        </el-table-column>
        <el-table-column label="签到时间" width="170">
          <template #default="{row}">{{ formatTime(row.signInTime) }}</template>
        </el-table-column>
        <el-table-column label="签退时间" width="170">
          <template #default="{row}">{{ formatTime(row.signOutTime) }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column prop="username" label="用户名" width="120" v-if="activeTab==='all'" />
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { getAttendances, getAllAttendances } from '@/api/approval'

const userStore = useUserStore()
const activeTab = ref('my')
const records = ref([])
const total = ref(0)
const page = ref(1)

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }

async function fetchData() {
  const fn = activeTab.value === 'all' ? getAllAttendances : getAttendances
  const res = await fn({ page: page.value, pageSize: 10 })
  records.value = res.data.records
  total.value = res.data.total
}

fetchData()
</script>
