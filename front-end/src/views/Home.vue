<template>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-8 mx-auto">
        <h1 class="mb-4 text-center" style="color: rgb(240, 228, 206); -webkit-text-stroke: 1.5px black;">¡Qué gusto verte por aquí en Blog Pakicetus, {{ username }}!</h1>
        <Form @postCreated="addPost" />
        <PostList :posts="posts" @updatePosts="fetchPosts" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Form from '@/components/Form.vue'
import PostList from '@/components/PostList.vue'
import api from '@/services/api'

const posts = ref([])
const username = ref('')

const fetchPosts = async () => {
  try {
    const response = await api.getPosts()
    posts.value = response.data
  } catch (error) {
    console.error('Error fetching posts:', error)
  }
}

const fetchUser = async (userId) => {
  try {
    const response = await api.getUser(userId)
    username.value = response.data.username
  } catch (error) {
    console.error('Error fetching user:', error)
  }
}

const addPost = (post) => {
  posts.value.push(post)
}

onMounted(() => {
  const userId = localStorage.getItem('user_id')
  if (userId) {
    fetchUser(userId)
  } else {
    console.error('No se encontró el user_id en localStorage.')
  }
  fetchPosts()
})
</script>
