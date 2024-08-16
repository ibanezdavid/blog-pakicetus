<template>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-8 mx-auto">
        <CommentForm :postId="Number(postId)" @commentCreated="addComment" />
        <CommentList :postId="Number(postId)" :comments="comments" @updateComments="fetchComments(postId)" />
      </div>
    </div>
  </div>
</template>

<script>
import CommentForm from '@/components/CommentForm.vue'
import CommentList from '@/components/CommentList.vue'
import api from '@/services/api'

export default {
  components: {
    CommentForm,
    CommentList
  },
  props: {
    postId: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      comments: [],
      error: null,
      loading: false
    };
  },
  methods: {
    async fetchComments() {
      this.loading = true;
      this.error = null;
      try {
        const response = await api.getCommentsPorPostId(Number(this.postId));
        this.comments = response.data;
      } catch (err) {
        this.error = 'Error fetching comments';
        console.error('Error fetching comments:', err);
      } finally {
        this.loading = false;
      }
    },
    async addComment(commentData) {
      this.comments.push(commentData);
    }
  },
  watch: {
    postId: {
      immediate: true,
      handler() {
        this.fetchComments();
      }
    }
  }
}
</script>
