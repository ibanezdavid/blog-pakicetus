<template>
    <div>
      <form class="submit-post">
        <div class="mb-3" id="submit-post-label-2">
          <label for="body" class="form-label">Comentario</label>
          <textarea
            v-model="data.comment.content"
            placeholder="Comentario"
            rows="3"
            class="form-control"
            id="body"
          ></textarea>
        </div>
  
        <div class="submit-post-button">
          <button
            @click.prevent="storeComment"
            :disabled="!data.comment.content"
            type="submit"
            class="btn btn-primary btn-sm"
            id="comentar"
          >
            Comentar
          </button>
        </div>
      </form>
    </div>
  </template>
  
  <script setup>
  import { reactive, onMounted, defineEmits, defineProps } from 'vue'
  import api from '@/services/api'
  
  const emit = defineEmits(['commentCreated'])
  const props = defineProps({
    postId: {
      type: Number,
      required: true
    }
  })
  
  const data = reactive({
    comment: {
      userId: 0,
      postId: props.postId,
      content: ''
    }
  })
  
  onMounted(() => {
    const userId = localStorage.getItem('user_id')
    if (userId) {
      data.comment.userId = userId
    } else {
      console.error('No se encontró el user_id en localStorage.')
    }
  })
  
  const storeComment = async () => {
    try {
      const response = await api.createComment(data.comment.postId, data.comment.userId, data.comment)
      data.comment.content = ''
      emit('commentCreated', response.data)
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
  #comentar{
    background-color: #ffcc88;
    border-color: black !important;
    color: black !important;
  }
  </style>
  