<template>
  <div>
    <h1 class="my-4 text-center" style="color: rgb(240, 228, 206); -webkit-text-stroke: 1.5px black;">¡Vamos a dar un vistazo a nuestros posts!</h1>
    <div v-if="error" class="alert alert-danger">{{ error }}</div>
    <div v-if="loading" class="spinner-border" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
  <div v-if="posts.length === 0 && !loading" class="alert alert-info">No hay posts disponibles.</div>
    <div v-else>
      <div v-for="post in posts" :key="post.id" class="card mb-4">
        <div class="card-body" id="card-body">
          <h5 v-if="!post.editing" class="card-title">{{ post.title }}</h5>
          <h5 v-else class="card-title">
            <input v-model="post.updatedTitle" class="form-control" />
          </h5>
          <h6 class="card-subtitle mb-2 text-muted">Autor: {{ post.user.username }}</h6>
          <p v-if="!post.editing" class="card-text">{{ post.content }}</p>
          <p v-else class="card-text">
            <textarea v-model="post.updatedContent" class="form-control"></textarea>
          </p>
          <div v-if="!post.editing" class="d-flex justify-content-between">
            <div>
                  <button 
                    class="btn btn-outline-primary btn-sm me-2" 
                    :disabled="post.liked || post.disliked" 
                    @click="likePost(post.id)"
                  >Like {{ post.likes }}</button>
                  <button 
                    class="btn btn-outline-secondary btn-sm me-2" 
                    :disabled="post.liked || post.disliked" 
                    @click="dislikePost(post.id)"
                  >Dislike {{ post.dislikes }}</button>
                  <router-link :to="{ name: 'PostComments', params: { postId: post.id } }" class="btn btn-outline-success btn-sm">Comentarios</router-link>
            </div>
            <div v-if="post.user.id == userId">
              <button @click="editPost(post)" class="btn btn-outline-warning btn-sm me-2">Editar</button>
              <button @click="deletePost(post.id)" class="btn btn-outline-danger btn-sm">Eliminar</button>
            </div>
          </div>
          <div v-else>
            <button @click="saveEdit(post)" class="btn btn-outline-success btn-sm me-2">Guardar</button>
            <button @click="cancelEdit(post)" class="btn btn-outline-secondary btn-sm">Cancelar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  import { useRouter } from 'vue-router'

  const router = useRouter()
  
  import api from '@/services/api'

  export default {
      props: {
          posts: {
              type: Array,
              required: true
          }
      },
      data() {
      return {
          error: null,
          loading: false,
          userId: localStorage.getItem('user_id')
      };
      },
      methods: {
          async likePost(postId) {
              const userId = localStorage.getItem('user_id')
              try {
                  const response = await api.likePost(postId, userId)
                  const post = this.posts.find(p => p.id === postId)
                  post.likes = response.data.likes
                  post.liked = true
                  post.disliked = false
              } catch (error) {
                  this.error = 'Error al dar like'
              }
          },
          async dislikePost(postId) {
              const userId = localStorage.getItem('user_id')
              try {
                  const response = await api.dislikePost(postId, userId)
                  const post = this.posts.find(p => p.id === postId)
                  post.dislikes = response.data.dislikes
                  post.disliked = true
                  post.liked = false
              } catch (error) {
                  this.error = 'Error al dar dislike'
              }
          },
          async deletePost(postId) {
            const userId = localStorage.getItem('user_id')
              try {
                  await api.deletePost(postId, userId);
                  this.$emit('updatePosts');
              } catch (error) {
                  this.error = 'Error al eliminar el post.';
              }
          },
          editPost(post) {
              post.editing = true;
              post.updatedTitle = post.title;
              post.updatedContent = post.content;
          },
          async saveEdit(post) {
              try {
                  const updatedPost = {
                      id: post.id,
                      title: post.updatedTitle,
                      content: post.updatedContent,
                      userId: this.userId
                  };
                  await api.editPost(post.id, updatedPost);
                  post.title = post.updatedTitle;
                  post.content = post.updatedContent;
                  post.editing = false;
              } catch (error) {
                  this.error = 'Error al guardar la edición.';
              }
          },
          cancelEdit(post) {
              post.editing = false;
          }
      }
  };
</script>

<style>
  #card-body{
    background-color: rgb(240, 228, 206);
  }
</style>