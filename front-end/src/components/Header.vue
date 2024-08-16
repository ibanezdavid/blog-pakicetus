<template>
    <header class="top-bar">
        <div class="left-buttons">
            <button class="btn btn-success" id="buttonA" @click="$router.push('/home')">Home</button>
            <button class="btn btn-danger" id="buttonB" @click="logout">Cerrar sesión</button>
        </div>
        <div class="title-header">
            <button class="principal-title-button" id="buttonC" @click="$router.push('/home')">Blog Pakicetus</button>
        </div>
    </header>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const logout = () => {
    router.push('/');
}
</script>

<style>
.top-bar {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding: 10px;
    position: relative;
}

.left-buttons {
    position: absolute;
    top: 0;
    right: 15px;
    display: flex;
}

.left-buttons button {
    margin: 10px;
    color: black;
}

.btn-success {
    color: black;
    background-color: #28a745;
    border-color: #28a745;
}

.title-header {
    display: flex;
    justify-content: center;
}

.principal-title-button {
    font-size: 55px;
    border: none;
    border-radius: 25px;
    background-color: rgb(226, 194, 48);
    margin: 15px;
    padding: 15px;
    color: black;
}

#buttonA{
    background-color: #ffcc88;
    border-color: #ffcc88;
}

#buttonB{
    background-color: #ffcc88;
    border-color: #ffcc88;
}

#buttonC{
    background-color: #ffcc88;
    border-color: #ffcc88;
}

</style>
