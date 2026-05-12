<template>
  <div class="layout">
    <header class="layout-header">
      <Navbar />
    </header>
    <div class="layout-body">
      <aside class="layout-aside">
        <Sidebar />
      </aside>
      <main class="layout-main">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import Sidebar from '@/components/common/Sidebar.vue'
import Navbar from '@/components/common/Navbar.vue'
</script>

<style scoped>
.layout { min-height: 100vh; background: var(--bg-primary); }
.layout-header {
  position: sticky; top: 0; z-index: 100;
  height: 48px; background: #fff;
  border-bottom: 0.5px solid var(--border-color);
}
.layout-body { display: flex; }
.layout-aside {
  position: sticky; top: 48px;
  width: 200px; height: calc(100vh - 48px);
  background: var(--bg-secondary);
  border-right: 0.5px solid var(--border-color);
  overflow-y: auto; flex-shrink: 0;
}
.layout-main {
  flex: 1; min-width: 0;
  min-height: calc(100vh - 48px);
  padding: var(--space-page);
}
</style>
