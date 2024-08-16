import { createRouter, createWebHistory } from "vue-router";
import Home from '../views/Home.vue';
import Login from '../views/Login.vue';
import Register from "../views/Register.vue";
import PostComments from '@/views/PostComments.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path:'/',
            name: 'login',
            component: Login

        },
        {
            path: '/home',
            name: 'home',
            component: Home
        },
        {
            path: '/register',
            name: 'register',
            component: Register
        },
        {
            path: '/post/:postId/comments',
            name: 'PostComments',
            component: PostComments,
            props: true
          },
    ]
});

export default router;
