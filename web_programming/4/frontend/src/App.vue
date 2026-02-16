<template>
  <div class="app">
    <Header />
    <main>
      <AuthForm
          v-if="!isAuthenticated"
          @login="handleLogin"
          @register="handleRegister"
          @error="(msg) => showNotification(msg, 'error')"
      />
      <div v-else class="main-content">
        <div class="controls">
          <div class="user-info">
            <h3 class="username-label">Текущий пользователь: {{ username }}</h3>
            <button @click="logout" class="btn">Выйти</button>
          </div>
          <GraphCanvas
              :r="currentR"
              :points="points"
              @add-point="handleCanvasAddPoint"
              @error="(msg) => showNotification(msg, 'error')"
          />
          <PointForm
              :currentR="currentR"
              @update:r="val => currentR = val"
              @submit-point="addPoint"
              @error="(msg) => showNotification(msg, 'error')"
          />
          <button @click="clearPoints" class="btn">
            Очистить историю
          </button>
        </div>
        <ResultsTable :points="points" />
      </div>
    </main>

    <div class="notifications-container">
      <transition-group name="notification">
        <div
            v-for="notification in notifications"
            :key="notification.id"
            class="notification"
            :class="notification.type"
        >
          {{ notification.message }}
        </div>
      </transition-group>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Header from './components/Header.vue';
import AuthForm from './components/AuthForm.vue';
import PointForm from './components/PointForm.vue';
import ResultsTable from './components/ResultsTable.vue';
import GraphCanvas from './components/GraphCanvas.vue';

axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.withCredentials = true;

export default {
  name: 'App',
  components: {
    Header,
    AuthForm,
    PointForm,
    GraphCanvas,
    ResultsTable
  },
  data() {
    return {
      isAuthenticated: false,
      username: '',
      points: [],
      currentR: 1,
      error: null,
      notifications: []
    };
  },
  async mounted() {
    try {
      const response = await axios.get('/api/auth/me');
      this.isAuthenticated = true;
      this.username = response.data.username;
      await this.fetchPoints();
    } catch (e) {
      console.log('Не авторизован');
      this.isAuthenticated = false;
      this.username = '';
      this.points = [];
    }
  },
  methods: {
    handleCanvasAddPoint(pointData) {
      this.addPoint(pointData);
    },
    showNotification(message, type = 'info') {
      const id = Date.now() + Math.random();
      this.notifications.push({ id, message, type });
      setTimeout(() => {
        this.notifications = this.notifications.filter(n => n.id !== id);
      }, 3000);
    },

    async handleLogin(credentials) {
      try {
        await axios.post('/api/auth/login', credentials);
        this.isAuthenticated = true;
        this.username = credentials.username;
        await this.fetchPoints();
        this.showNotification('Вход выполнен', 'success');
      } catch (error) {
        this.showNotification(this.extractErrorMessage(error) || 'Ошибка входа', 'error');
      }
    },

    async handleRegister(credentials) {
      try {
        await axios.post('/api/auth/register', credentials);
        this.showNotification('Регистрация успешна, теперь войдите', 'success');
      } catch (error) {
        this.showNotification(this.extractErrorMessage(error) || 'Ошибка регистрации', 'error');
      }
    },

    async logout() {
      try {
        await axios.post('/api/auth/logout');
        this.isAuthenticated = false;
        this.username = '';
        this.points = [];
        this.showNotification('Выход выполнен', 'success');
      } catch (error) {
        this.showNotification('Ошибка при выходе', 'error');
      }
    },

    async fetchPoints() {
      const response = await axios.get('/api/points');
      this.points = response.data;
    },

    async addPoint(pointData) {
      try {
        const response = await axios.post('/api/points', pointData);
        this.points.push(response.data);
        this.showNotification('Точка добавлена', 'success');
      } catch (error) {
        this.showNotification(this.extractErrorMessage(error) || 'Ошибка при добавлении точки', 'error');
      }
    },

    async clearPoints() {
      try {
        await axios.delete('/api/points');
        this.points = [];
        this.showNotification('История очищена', 'success');
      } catch (error) {
        this.showNotification(
            this.extractErrorMessage(error) || 'Ошибка при очистке истории',
            'error'
        );
      }
    },

    extractErrorMessage(error) {
      if (!error.response) return error.message;
      const data = error.response.data;
      if (data && typeof data === 'object') {
        if (data.message) return data.message;
        return Object.values(data).join('; ');
      }
      return data;
    }
  }
};
</script>