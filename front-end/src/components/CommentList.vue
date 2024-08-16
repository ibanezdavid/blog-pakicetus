<template>
    <div>
      <h1 class="my-4 text-center" style="color: rgb(240, 228, 206); -webkit-text-stroke: 1.5px black;">¡Vamos a dar un vistazo a los comentarios!</h1>
      <div v-if="error" class="alert alert-danger">{{ error }}</div>
      <div v-if="loading" class="spinner-border" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
      <div v-if="comments.length === 0 && !loading" class="alert alert-info">No hay comentarios disponibles.</div>
      <div v-else>
        <div v-for="comment in comments" :key="comment.id" class="card mb-4">
          <div class="card-body" id="card-body">
            <h6 class="card-subtitle mb-2 text-muted">Autor: {{ comment.user?.username }}</h6>
            <p v-if="!comment.editing" class="card-text">{{ comment.content }}</p>
            <p v-else class="card-text">
              <textarea v-model="comment.updatedContent" class="form-control"></textarea>
            </p>
            <div v-if="!comment.editing" class="d-flex justify-content-between">
              <div v-if="comment.user?.id == userId">
                <button @click="editComment(comment)" class="btn btn-outline-warning btn-sm me-2">Editar</button>
                <button @click="deleteComment(comment.id)" class="btn btn-outline-danger btn-sm">Eliminar</button>
              </div>
            </div>
            <div v-else>
              <button @click="saveEdit(comment)" class="btn btn-outline-success btn-sm me-2">Guardar</button>
              <button @click="cancelEdit(comment)" class="btn btn-outline-secondary btn-sm">Cancelar</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import api from '@/services/api'
  
  export default {
    props: {
      comments: {
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
      async deleteComment(commentId) {
        try {
          await api.deleteComment(commentId);
          this.$emit('updateComments');
        } catch (error) {
          this.error = 'Error al eliminar el comentario.';
        }
      },
      editComment(comment) {
        comment.editing = true;
        comment.updatedContent = comment.content;
      },
      async saveEdit(comment) {
        try {
          const updatedComment = {
            content: comment.updatedContent
          };
          await api.editComment(comment.id, updatedComment);
          comment.content = comment.updatedContent;
          comment.editing = false;
        } catch (error) {
          this.error = 'Error al guardar la edición.';
        }
      },
      cancelEdit(comment) {
        comment.editing = false;
      }
    }
  };
  </script>

<style>
#card-body{
  background-color: rgb(240, 228, 206);
}
button{
  color: black !important;
}
</style>
  