<template>
  <div class="mt-5 d-flex justify-content-center">
    <div class="login-container bg-light p-4 rounded" id="registerA">
      <form @submit.prevent="registerUser" class="w-100">
        <h3 class="mb-4 text-center">¡Regístrate y únete a Blog Pakicetus!</h3>
        <div class="mb-3">
          <label for="username" class="form-label">Usuario:</label>
          <input type="text" id="username" v-model="formData.username" class="form-control" required placeholder="Usuario">
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">Contraseña:</label>
          <input type="password" id="password" v-model="formData.password" class="form-control" required placeholder="Contraseña">
        </div>
        <div class="d-grid">
          <button type="submit" class="btn btn-primary btn-block" id="registrarse">Registrarse</button>
        </div>
        <p class="mt-3 text-center">
          ¿Ya te has unido a nuestra comunidad? 
          <router-link to="/" style="color: rgb(240, 228, 206); -webkit-text-stroke: 1px black;">¡Inicia sesión aquí!</router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import router from '../router';
import api from '../services/api';
  
  export default {
    data() {
      return {
        formData: {
          username: '',
          password: ''
        }
      };
    },
    methods: {
      async registerUser() {
        try {
          const response = await api.createUser(this.formData);
          console.log('User registered:', response.data);
          router.push('/')
        } catch (error) {
          console.error('Error registering user:', error);
        }
      }
    }
  };
</script>

<style>
#registerA {
  background-color: rgb(240, 228, 206) !important;
}
#registrarse{
  background-color: #ffcc88;
  border-color: black !important;
  color: black !important;
}
</style>