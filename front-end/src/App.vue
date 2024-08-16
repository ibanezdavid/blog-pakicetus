<template>
  <div>
    <Header2 v-if="showHeader2" />
    <Header v-else />
    <router-view />
  </div>
</template>

<script>
import Header from './components/Header.vue';
import Header2 from './components/Header2.vue';

export default {
  components: {
    Header,
    Header2
  },
  computed: {
    showHeader2() {
      const path = this.$route.path;
      return path === '/' || path === '/register';
    }
  }
}
</script>
