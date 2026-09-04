<template>
  <div class="point-form">
    <p>Проверка точки</p>
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
    <button @click="submitPoint" class="btn">Проверить</button>
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
  emits: ['update:r', 'submit-point', 'error'],
  data() {
    return {
      x: 0,
      y: 0,
      r: this.currentR,
    };
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
      if (this.x <= -5) {
        this.$emit('error', 'Значение X должно быть больше -5');
        return;
      }
      if (this.x >= 5) {
        this.$emit('error', 'Значение X должно быть меньше 5');
        return;
      }
      if (this.y <= -5) {
        this.$emit('error', 'Значение Y должно быть больше -5');
        return;
      }
      if (this.y >= 3) {
        this.$emit('error', 'Значение Y должно быть меньше 3');
        return;
      }
      if (this.r <= 0) {
        this.$emit('error', 'Значение R должно быть больше 0');
        return;
      }
      if (this.r >= 5) {
        this.$emit('error', 'Значение R должно быть меньше 5');
        return;
      }
      this.$emit('submit-point', { x: this.x, y: this.y, r: this.r });
    }
  }
};
</script>