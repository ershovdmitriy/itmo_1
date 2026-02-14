<template>
  <div class="point-form">
    <h3>Проверка точки</h3>
    <div>
      <label>X (-5 .. 5):</label>
      <input type="number" step="any" v-model.number="x" />
    </div>
    <div>
      <label>Y (-5 .. 3):</label>
      <input type="number" step="any" v-model.number="y" />
    </div>
    <div>
      <label>R (0 .. 5):</label>
      <input type="number" step="any" v-model.number="r" @input="updateR" />
    </div>
    <button @click="submitPoint" :disabled="!isValid" class="btn">Проверить</button>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script>
export default {
  props: {
    currentR: {
      type: Number,
      required: true
    }
  },
  emits: ['update:r', 'submit-point'],
  data() {
    return {
      x: 0,
      y: 0,
      r: this.currentR,
      error: ''
    };
  },
  computed: {
    isValid() {
      return this.x > -5 && this.x < 5 &&
          this.y > -5 && this.y < 3 &&
          this.r > 0 && this.r < 5;
    }
  },
  watch: {
    currentR(newVal) {
      this.r = newVal;
    }
  },
  methods: {
    updateR() {
      this.$emit('update:r', this.r);
    },
    submitPoint() {
      if (!this.isValid) {
        this.error = 'Значения выходят за допустимые пределы';
        return;
      }
      this.$emit('submit-point', { x: this.x, y: this.y, r: this.r });
      this.error = '';
    }
  }
};
</script>