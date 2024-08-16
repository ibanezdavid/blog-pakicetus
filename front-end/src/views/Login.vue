<template>
  <div class="mt-5 d-flex justify-content-center" >
    <div class="login-container bg-light p-4 rounded" id="loginA">
      <form @submit.prevent="login" class="w-100">
        <h3 class="mb-4 text-center">¡Inicia sesión y únete a la diversión!</h3>
        <div class="mb-3">
          <label for="username" class="form-label">Usuario</label>
          <input v-model="username" type="text" class="form-control" id="username" required placeholder="Usuario">
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">Contraseña</label>
          <input v-model="password" type="password" class="form-control" id="password" required placeholder="Contraseña">
        </div>
        <div class="d-grid">
          <button type="submit" class="btn btn-primary btn-block" id="login">Iniciar Sesión</button>
        </div>
        <p class="mt-3 text-center">
          ¿Aún no te has unido a nuestra comunidad? 
          <router-link to="/register" style="color: rgb(240, 228, 206); -webkit-text-stroke: 1px black;">¡Regístrate aquí!</router-link>
        </p>
      </form>
    </div>
    
  </div>
</template>


<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/services/api';

const username = ref('');
const password = ref('');
const router = useRouter();

const login = async () => {
  try {
    const response = await api.login({ username: username.value, password: password.value });
    const userId = response.data.id;
    localStorage.setItem('user_id', userId);
    router.push({ name: 'home' });
  } catch (error) {
    console.error('Error al iniciar sesión:', error);
  }
};
</script>

<style>
  p{
    font-size: 18px;
  }
  #loginA {
    background-color: rgb(240, 228, 206) !important;
  }
  #login{
    background-color: #ffcc88;
    border-color: black !important;
    color: black !important;
  }
</style>
