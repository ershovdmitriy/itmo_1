
const GraphController = {
    canvas: null,
    ctx: null,
    rValue: null,

    init: function() {
        this.canvas = document.getElementById('myCanvas');
        if (!this.canvas) {
            console.error('Canvas not found!');
            return;
        }
        this.ctx = this.canvas.getContext('2d');
        this.bindEvents();
        this.drawGraph();
        this.drawMarkersFromTable();
    },

    bindEvents: function() {
        // Обработчик клика по canvas
        this.canvas.addEventListener('click', (e) => this.handleCanvasClick(e));

        // Обработчик для spinner R (PrimeFaces)
        this.bindSpinnerEvents();

        // Обработчик для radio buttons R
        this.bindRadioEvents();

        // Наблюдатель за изменениями DOM для обновления графика
        this.setupMutationObserver();
    },

    bindSpinnerEvents: function() {
        // PrimeFaces spinner генерирует input с id, заканчивающимся на "_input"
        const spinnerInput = document.querySelector('[id*=":r_input"], [id$=":r_input"]');
        if (spinnerInput) {
            spinnerInput.addEventListener('input', () => {
                setTimeout(() => {
                    this.updateRFromSpinner();
                    this.drawGraph();
                    this.drawMarkersFromTable();
                }, 100);
            });
            spinnerInput.addEventListener('change', () => {
                this.updateRFromSpinner();
                this.drawGraph();
                this.drawMarkersFromTable();
            });
        }
    },

    bindRadioEvents: function() {
        // Для обычных radio buttons R
        const rRadios = document.querySelectorAll('[name*=":r"], [name$=":r"]');
        rRadios.forEach(radio => {
            radio.addEventListener('change', () => {
                setTimeout(() => {
                    this.updateRFromRadios();
                    this.drawGraph();
                    this.drawMarkersFromTable();
                }, 50);
            });
        });
    },

    updateRFromSpinner: function() {
        // Получаем значение из spinner
        const spinnerInput = document.querySelector('[id*=":r_input"], [id$=":r_input"]');
        if (spinnerInput && spinnerInput.value) {
            this.rValue = parseFloat(spinnerInput.value);
            console.log('R updated from spinner:', this.rValue);
        }
    },

    updateRFromRadios: function() {
        // Получаем значение из radio buttons
        const rRadios = document.querySelectorAll('[name*=":r"], [name$=":r"]');
        for (let radio of rRadios) {
            if (radio.checked) {
                this.rValue = parseFloat(radio.value);
                console.log('R updated from radio:', this.rValue);
                break;
            }
        }
    },

    getR: function() {
        // Сначала проверяем spinner
        const spinnerInput = document.querySelector('[id*=":r_input"], [id$=":r_input"]');
        if (spinnerInput && spinnerInput.value) {
            return parseFloat(spinnerInput.value);
        }

        // Затем проверяем radio buttons
        const rRadios = document.querySelectorAll('[name*=":r"], [name$=":r"]');
        for (let radio of rRadios) {
            if (radio.checked) {
                return parseFloat(radio.value);
            }
        }

        return -1;
    },

    toCanvasX: function(x) {
        return this.canvas.width / 2 + x * 50;
    },

    toCanvasY: function(y) {
        return this.canvas.height / 2 - y * 50;
    },

    fromCanvasX: function(canvasX) {
        return (canvasX - this.canvas.width / 2) / 50;
    },

    fromCanvasY: function(canvasY) {
        return -(canvasY - this.canvas.height / 2) / 50;
    },

    drawGrid: function() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.ctx.strokeStyle = '#e0e0e0';
        this.ctx.lineWidth = 1;

        for (let x = -4; x <= 4; x++) {
            if (x === 0) continue;
            this.ctx.beginPath();
            this.ctx.moveTo(this.toCanvasX(x), 0);
            this.ctx.lineTo(this.toCanvasX(x), this.canvas.height);
            this.ctx.stroke();
        }

        for (let y = -4; y <= 4; y++) {
            if (y === 0) continue;
            this.ctx.beginPath();
            this.ctx.moveTo(0, this.toCanvasY(y));
            this.ctx.lineTo(this.canvas.width, this.toCanvasY(y));
            this.ctx.stroke();
        }
    },

    drawAxes: function() {
        this.ctx.strokeStyle = '#000';
        this.ctx.lineWidth = 2;
        this.ctx.font = 'bold 16px Arial';

        // Ось X
        this.ctx.beginPath();
        this.ctx.moveTo(0, this.canvas.height / 2);
        this.ctx.lineTo(this.canvas.width, this.canvas.height / 2);
        this.ctx.stroke();

        // Ось Y
        this.ctx.beginPath();
        this.ctx.moveTo(this.canvas.width / 2, 0);
        this.ctx.lineTo(this.canvas.width / 2, this.canvas.height);
        this.ctx.stroke();

        this.ctx.fillStyle = '#000';

        // Метки X
        for (let x = -4; x <= 4; x++) {
            if (x === 0) continue;
            this.ctx.fillText(x.toString(), this.toCanvasX(x) - 5, this.canvas.height / 2 + 20);
            this.ctx.beginPath();
            this.ctx.moveTo(this.toCanvasX(x), this.canvas.height / 2 - 5);
            this.ctx.lineTo(this.toCanvasX(x), this.canvas.height / 2 + 5);
            this.ctx.stroke();
        }

        // Метки Y
        for (let y = -4; y <= 4; y++) {
            if (y === 0) continue;
            this.ctx.fillText(y.toString(), this.canvas.width / 2 + 15, this.toCanvasY(y) + 5);
            this.ctx.beginPath();
            this.ctx.moveTo(this.canvas.width / 2 - 5, this.toCanvasY(y));
            this.ctx.lineTo(this.canvas.width / 2 + 5, this.toCanvasY(y));
            this.ctx.stroke();
        }

        this.ctx.fillText('0', this.canvas.width / 2 - 15, this.canvas.height / 2 + 20);
    },

    drawArea: function(R) {
        if (R <= 0) return;

        this.ctx.beginPath();

        // 1-й квадрант (четверть круга)
        this.ctx.arc(this.toCanvasX(0), this.toCanvasY(0), R * 50, 0, -Math.PI / 2, true);
        this.ctx.lineTo(this.toCanvasX(0), this.toCanvasY(0));

        // 4-й квадрант (треугольник)
        this.ctx.moveTo(this.toCanvasX(0), this.toCanvasY(0));
        this.ctx.lineTo(this.toCanvasX(0), this.toCanvasY(-R));
        this.ctx.lineTo(this.toCanvasX(-R/2), this.toCanvasY(0));
        this.ctx.closePath();

        // 3-й квадрант (прямоугольник)
        this.ctx.moveTo(this.toCanvasX(0), this.toCanvasY(0));
        this.ctx.lineTo(this.toCanvasX(0), this.toCanvasY(R/2));
        this.ctx.lineTo(this.toCanvasX(R), this.toCanvasY(R/2));
        this.ctx.lineTo(this.toCanvasX(R), this.toCanvasY(0));
        this.ctx.closePath();

        this.ctx.fillStyle = 'rgba(51, 119, 221, 0.8)';
        this.ctx.fill();
        this.ctx.strokeStyle = '#3377dd';
        this.ctx.lineWidth = 2;
        this.ctx.stroke();
    },

    drawMarker: function(x, y, hit) {
        this.ctx.save();
        this.ctx.beginPath();
        this.ctx.arc(this.toCanvasX(x), this.toCanvasY(y), 5, 0, Math.PI * 2);

        if (hit === true || hit === 'true' || hit === 'Попадание') {
            this.ctx.fillStyle = '#33dd77';
        } else {
            this.ctx.fillStyle = '#ff0000';
        }

        this.ctx.fill();
        this.ctx.strokeStyle = '#000';
        this.ctx.lineWidth = 1;
        this.ctx.stroke();
        this.ctx.restore();
    },

    drawMarkersFromTable: function() {
        // Ищем таблицу результатов - JSF генерирует ID с двоеточием
        const table = document.querySelector('[id*=":table_result"], [id$=":table_result"], #table_result');
        if (!table) {
            console.log('Table not found, trying alternative selectors...');
            // Пробуем другие варианты селекторов
            const allTables = document.querySelectorAll('table');
            for (let table of allTables) {
                if (table.querySelector('td') && table.querySelector('td').textContent.includes('X')) {
                    this.parseTableData(table);
                    return;
                }
            }
            return;
        }

        this.parseTableData(table);
    },

    parseTableData: function(table) {
        // Пропускаем заголовок
        const rows = table.querySelectorAll('tr:not(:first-child)');
        rows.forEach(row => {
            const cells = row.querySelectorAll('td');
            if (cells.length >= 4) {
                try {
                    const x = parseInt(cells[0].textContent.trim());
                    const y = parseFloat(cells[1].textContent.trim());
                    const result = cells[3].textContent.trim();
                    this.drawMarker(x, y, result);
                } catch (e) {
                    console.log('Error parsing row:', cells);
                }
            }
        });
    },

    drawGraph: function() {
        const R = this.getR();
        this.rValue = R;

        this.drawGrid();
        if (R > 0) this.drawArea(R);
        this.drawAxes();

        // Отображаем текущее значение R
        this.ctx.fillStyle = '#ff0000';
        this.ctx.font = 'bold 16px Arial';
        if (R <= 0) {
            this.ctx.fillText('R - не выбрано', 20, 30);
        } else {
            this.ctx.fillText(`R = ${R}`, 20, 30);
        }
    },

    handleCanvasClick: function(event) {
        const R = this.getR();

        if (R <= 0) {
            alert("Необходимо выбрать R для взаимодействия с графиком");
            return;
        }

        const rect = this.canvas.getBoundingClientRect();
        const canvasX = event.clientX - rect.left;
        const canvasY = event.clientY - rect.top;

        let mathX = this.fromCanvasX(canvasX);
        let mathY = this.fromCanvasY(canvasY);

        // Округляем X до целого
        mathX = Math.round(mathX);

        // Ограничиваем значения согласно диапазонам
        if (mathX < -4) mathX = -4;
        if (mathX > 4) mathX = 4;
        if (mathY < -3) mathY = -3;
        if (mathY > 5) mathY = 5;

        // Устанавливаем значения в форму JSF
        this.setFormValues(mathX, mathY, R);

        // Отправляем форму
        this.submitForm();
    },

    setFormValues: function(x, y, r) {
        // Устанавливаем X (radio buttons)
        const xRadios = document.querySelectorAll('[name*=":x"], [name$=":x"]');
        xRadios.forEach(radio => {
            if (parseInt(radio.value) === x) {
                radio.checked = true;
                // Триггерим событие для JSF
                radio.dispatchEvent(new Event('change', { bubbles: true }));
            }
        });

        // Устанавливаем Y
        const yInput = document.querySelector('[id*=":y"], [id$=":y"], #y');
        if (yInput) {
            yInput.value = y.toFixed(2);
            yInput.dispatchEvent(new Event('input', { bubbles: true }));
            yInput.dispatchEvent(new Event('change', { bubbles: true }));
        }

        // R уже установлен
    },

    submitForm: function() {
        // Находим кнопку отправки
        const submitBtn = document.querySelector('[id*=":submit"], [type="submit"]');
        if (submitBtn) {
            submitBtn.click();
        } else {
            // Пробуем отправить форму напрямую
            const form = document.querySelector('form');
            if (form) {
                form.submit();
            }
        }
    },

    setupMutationObserver: function() {
        // Наблюдаем за изменениями в таблице результатов
        const observer = new MutationObserver((mutations) => {
            mutations.forEach((mutation) => {
                if (mutation.type === 'childList') {
                    // Перерисовываем маркеры после обновления таблицы
                    setTimeout(() => {
                        this.drawGraph();
                        this.drawMarkersFromTable();
                    }, 100);
                }
            });
        });

        // Наблюдаем за всей страницей для обнаружения таблицы
        observer.observe(document.body, {
            childList: true,
            subtree: true
        });
    },

    refresh: function() {
        this.drawGraph();
        this.drawMarkersFromTable();
    }
};

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    // Даем JSF время на инициализацию компонентов
    setTimeout(() => {
        GraphController.init();
    }, 500);

    // Также перерисовываем при каждом AJAX-обновлении (для PrimeFaces)
    if (window.PrimeFaces) {
        PrimeFaces.ajax.AjaxUtils.registerOnsuccessCallback(function(data) {
            setTimeout(() => {
                GraphController.refresh();
            }, 300);
        });
    }
});

// Глобальная функция для ручного обновления (можно вызывать из JSF)
window.refreshGraph = function() {
    GraphController.refresh();
};