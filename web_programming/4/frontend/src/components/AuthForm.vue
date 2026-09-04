<template>
  <div class="auth-form">
    <p>{{ isLogin ? 'Вход' : 'Регистрация' }}</p>
    <form @submit.prevent="handleSubmit">
      <div>
        <label>Логин:</label>
        <input type="text" v-model="username"/>
      </div>
      <div>
        <label>Пароль:</label>
        <input type="password" v-model="password"/>
      </div>
      <button type="submit" class="btn">{{ isLogin ? 'Войти' : 'Зарегистрироваться' }}</button>
      <button type="button" @click="toggleMode" class="btn">
        {{ isLogin ? 'Нет аккаунта? Зарегистрироваться' : 'Уже есть аккаунт? Войти' }}
      </button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      isLogin: true,
    };
  },
  methods: {
    toggleMode() {
      this.isLogin = !this.isLogin;
    },
    handleSubmit() {
      if (!this.username || !this.password) {
        this.$emit('error', 'Заполните все поля');
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