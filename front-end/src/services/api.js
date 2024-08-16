import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
})

export default {
  getPosts: () => api.get('/posts'),
  getPost: (id) => api.get(`/posts/${id}`),
  createPost: (post) => api.post('/posts/create', post),
  deletePost: (postId, userId) => api.delete(`/posts/${postId}/delete?userId=${userId}`),
  createUser: (user) => api.post('/users/register', user),
  login: (credentials) => api.post('/auth/login', credentials),
  getUser: (id) => api.get(`/users/${id}`),
  likePost: (postId, userId) => api.post(`/posts/${postId}/like?userId=${userId}`),
  dislikePost: (postId, userId) => api.post(`/posts/${postId}/dislike?userId=${userId}`),
  editPost: (postId, updatedPost) => api.put(`/posts/${postId}/edit`, updatedPost),
  getCommentsPorPostId: (postId) => api.get(`/comments/post/${Number(postId)}`),
  createComment: (postId, userId, comment) => api.post(`/comments/post/${postId}/user/${userId}`, comment),
  deleteComment: (commentId) => api.delete(`/comments/${commentId}`),
  editComment: (commentId, updatedComment) => api.put(`/comments/${commentId}`, updatedComment),
}
