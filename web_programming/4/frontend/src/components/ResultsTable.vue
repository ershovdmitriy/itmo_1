<template>
  <div>
    <table class="results-table" v-if="sortedPoints.length">
      <thead>
      <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Результат</th>
        <th>Время запроса</th>
        <th>Время обработки (мс)</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="point in sortedPoints" :key="point.id">
        <td>{{ point.x }}</td>
        <td>{{ point.y }}</td>
        <td>{{ point.r }}</td>
        <td :class="{ hit: point.hit, miss: !point.hit }">
          {{ point.hit ? 'Попадание' : 'Промах' }}
        </td>
        <td>{{ formatDate(point.createdAt) }}</td>
        <td>{{ formatExecutionTime(point.executionTime) }}</td>
      </tr>
      </tbody>
    </table>
    <p v-else class="no-data">Нет сохранённых результатов</p>
  </div>
</template>

<script>
export default {
  name: 'ResultsTable',
  props: {
    points: {
      type: Array,
      required: true
    }
  },
  computed: {
    sortedPoints() {
      return [...this.points].sort((a, b) => {
        return new Date(b.createdAt) - new Date(a.createdAt);
      });
    }
  },
  methods: {
    formatDate(isoString) {
      if (!isoString) return '';
      const date = new Date(isoString);
      return date.toLocaleString();
    },
    formatExecutionTime(microseconds) {
      if (microseconds === undefined || microseconds === null) return '';
      return (microseconds / 1000).toFixed(3);
    }
  }
};
</script>