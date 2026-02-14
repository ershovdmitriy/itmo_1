<template>
  <div class="auth-form">
    <h2>{{ isLogin ? 'Вход' : 'Регистрация' }}</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label>Логин:</label>
        <input type="text" v-model="username" required />
      </div>
      <div>
        <label>Пароль:</label>
        <input type="password" v-model="password" required />
      </div>
      <button type="submit" class="btn">{{ isLogin ? 'Войти' : 'Зарегистрироваться' }}</button>
      <button type="button" @click="toggleMode" class="btn">
        {{ isLogin ? 'Нет аккаунта? Зарегистрироваться' : 'Уже есть аккаунт? Войти' }}
      </button>
    </form>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      isLogin: true,
      error: ''
    };
  },
  methods: {
    toggleMode() {
      this.isLogin = !this.isLogin;
      this.error = '';
    },
    handleSubmit() {
      if (!this.username || !this.password) {
        this.error = 'Заполните все поля';
        return;
      }
      const credentials = { username: this.username, password: this.password };
      if (this.isLogin) {
        this.$emit('login', credentials);
      } else {
        this.$emit('register', credentials);
      }
    }
  }
};
</script>