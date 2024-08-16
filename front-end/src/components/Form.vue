<template>
  <div>
    <form class="submit-post" id="formA">
      <div class="mb-3" id="submit-post-label-1">
        <label for="title" class="form-label">Título</label>
        <input
          v-model="data.post.title"
          placeholder="Título"
          type="text"
          class="form-control"
          id="title"
        />
      </div>

      <div class="mb-3" id="submit-post-label-2">
        <label for="body" class="form-label">Contenido</label>
        <textarea
          v-model="data.post.content"
          placeholder="Contenido"
          rows="3"
          class="form-control"
          id="body"
        ></textarea>
      </div>

      <div class="submit-post-button">
        <button
          @click.prevent="storePost"
          :disabled="!data.post.title || !data.post.content"
          type="submit"
          class="btn btn-primary btn-sm"
          id="postear"
        >
          Postear
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, onMounted, defineEmits } from 'vue'
import api from '@/services/api'
import moment from 'moment'

const emit = defineEmits(['postCreated'])

const data = reactive({
  post: {
    userId: 0,
    title: '',
    content: ''
  }
})

onMounted(() => {
  const userId = localStorage.getItem('user_id')
  if (userId) {
    data.post.userId = userId
  } else {
    console.error('No se encontró el user_id en localStorage.')
  }
})

const storePost = async () => {
  const post = {
    ...data.post,
    time: moment().format('MMMM Do YYYY, h:mm:ss a')
  }

  try {
    const response = await api.createPost(post)
    data.post.title = ''
    data.post.content = ''
    emit('postCreated', response.data)
  } catch (error) {
    console.error('Error posting:', error)
  }
}
</script>

<style>
.submit-post {
  background-color: rgb(255, 240, 220);
  padding: 20px;
  border-radius: 25px;
  margin: 15px;
  font-size: 17px;
}
#submit-post-label-1, #submit-post-label-2 {
  margin: 10px;
}
.submit-post-button {
  display: flex;
  justify-content: center;
  margin: 20px 0px 0px 0px;
}
#formA{
  background-color: rgb(240, 228, 206);
}
#postear{
  background-color: #ffcc88;
  border-color: black !important;
  color: black !important;
}
</style>
