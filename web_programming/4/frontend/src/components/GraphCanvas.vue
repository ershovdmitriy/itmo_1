<template>
  <canvas
      ref="canvas"
      class="graph-canvas"
      :width="width"
      :height="height"
      @click="handleCanvasClick"
  ></canvas>
</template>

<script>
import { ref, onMounted, watch } from 'vue';

export default {
  name: 'GraphCanvas',
  props: {
    r: {
      type: Number,
      required: true,
      validator: value => value >= 0 && value <= 5,
    },
    points: {
      type: Array,
      default: () => [],
    },
    width: {
      type: Number,
      default: 500,
    },
    height: {
      type: Number,
      default: 500,
    },
  },
  emits: ['add-point', 'error'],
  setup(props, { emit }) {
    const canvas = ref(null);
    let ctx = null;

    const toCanvasX = (x) => props.width / 2 + x * 50;
    const toCanvasY = (y) => props.height / 2 - y * 50;

    const fromCanvasX = (canvasX) => (canvasX - props.width / 2) / 50;
    const fromCanvasY = (canvasY) => -(canvasY - props.height / 2) / 50;

    const drawGrid = () => {
      ctx.strokeStyle = '#e0e0e0';
      ctx.lineWidth = 1;

      for (let x = -4; x <= 4; x++) {
        if (x === 0) continue;
        ctx.beginPath();
        ctx.moveTo(toCanvasX(x), 0);
        ctx.lineTo(toCanvasX(x), props.height);
        ctx.stroke();
      }

      for (let y = -4; y <= 4; y++) {
        if (y === 0) continue;
        ctx.beginPath();
        ctx.moveTo(0, toCanvasY(y));
        ctx.lineTo(props.width, toCanvasY(y));
        ctx.stroke();
      }
    };

    const drawAxes = () => {
      ctx.strokeStyle = '#000';
      ctx.lineWidth = 2;
      ctx.font = 'bold 16px Arial';
      ctx.fillStyle = '#000';

      ctx.beginPath();
      ctx.moveTo(0, props.height / 2);
      ctx.lineTo(props.width, props.height / 2);
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(props.width / 2, 0);
      ctx.lineTo(props.width / 2, props.height);
      ctx.stroke();

      for (let x = -4; x <= 4; x++) {
        if (x === 0) continue;
        ctx.fillText(x.toString(), toCanvasX(x) - 10, props.height / 2 + 25);
        ctx.beginPath();
        ctx.moveTo(toCanvasX(x), props.height / 2 - 5);
        ctx.lineTo(toCanvasX(x), props.height / 2 + 5);
        ctx.stroke();
      }

      for (let y = -4; y <= 4; y++) {
        if (y === 0) continue;
        ctx.fillText(y.toString(), props.width / 2 + 10, toCanvasY(y) + 5);
        ctx.beginPath();
        ctx.moveTo(props.width / 2 - 5, toCanvasY(y));
        ctx.lineTo(props.width / 2 + 5, toCanvasY(y));
        ctx.stroke();
      }

      ctx.fillText('0', props.width / 2 - 20, props.height / 2 + 25);
    };

    const drawArea = (R) => {
      if (R <= 0) return;

      ctx.fillStyle = 'rgba(51, 119, 221, 0.8)';
      ctx.strokeStyle = '#3377dd';
      ctx.lineWidth = 2;
      ctx.beginPath();

      ctx.moveTo(toCanvasX(0), toCanvasY(0));
      ctx.arc(toCanvasX(0), toCanvasY(0), R * 25, Math.PI / 2, 0, true);
      ctx.lineTo(toCanvasX(0), toCanvasY(0));
      ctx.closePath();

      ctx.moveTo(toCanvasX(0), toCanvasY(0));
      ctx.lineTo(toCanvasX(-R/2), toCanvasY(0));
      ctx.lineTo(toCanvasX(-R/2), toCanvasY(R));
      ctx.lineTo(toCanvasX(0), toCanvasY(R));
      ctx.closePath();

      ctx.moveTo(toCanvasX(0), toCanvasY(0));
      ctx.lineTo(toCanvasX(-R/2), toCanvasY(0));
      ctx.lineTo(toCanvasX(0), toCanvasY(-R));
      ctx.closePath();

      ctx.fill();
      ctx.stroke();
    };

    const drawMarkers = () => {
      props.points.forEach(point => {
        const { x, y, hit } = point;
        ctx.save();
        ctx.beginPath();
        ctx.arc(toCanvasX(x), toCanvasY(y), 5, 0, Math.PI * 2);
        ctx.fillStyle = hit ? '#33dd77' : '#ff0000';
        ctx.fill();
        ctx.strokeStyle = '#000';
        ctx.lineWidth = 1.5;
        ctx.stroke();
        ctx.restore();
      });
    };

    const draw = () => {
      if (!ctx) return;

      ctx.clearRect(0, 0, props.width, props.height);

      drawGrid();
      drawArea(props.r);
      drawAxes();

      ctx.font = 'bold 16px Arial';
      ctx.fillStyle = '#ff0000';
      if (props.r <= 0) {
        ctx.fillText('R не выбран', 20, 30);
      } else {
        ctx.fillText(`R = ${props.r}`, 20, 30);
      }

      drawMarkers();
    };

    const handleCanvasClick = (event) => {
      if (props.r <= 0) {
        emit('error', 'Выберите радиус R');
        return;
      }

      const rect = canvas.value.getBoundingClientRect();
      const canvasX = event.clientX - rect.left;
      const canvasY = event.clientY - rect.top;

      let mathX = fromCanvasX(canvasX);
      let mathY = fromCanvasY(canvasY);

      mathX = Math.round(mathX * 100) / 100;
      mathY = Math.round(mathY * 100) / 100;

      if (mathX < -5) mathX = -5;
      if (mathX > 5) mathX = 5;
      if (mathY < -5) mathY = -5;
      if (mathY > 3) mathY = 3;

      emit('add-point', { x: mathX, y: mathY, r: props.r });
    };

    onMounted(() => {
      ctx = canvas.value.getContext('2d');
      draw();
    });

    watch(
        () => [props.r, props.points],
        () => {
          draw();
        },
        { deep: true, immediate: true }
    );

    return {
      canvas,
      handleCanvasClick,
    };
  },
};
</script>