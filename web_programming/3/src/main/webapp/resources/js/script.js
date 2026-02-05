document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById('myCanvas');
    const ctx = canvas.getContext('2d');
    const form = document.getElementById('main_form');

    const xInputs = document.querySelectorAll('input[name$=":x"]');
    const yInput = document.getElementById('main_form:y');
    const rSpinner = document.querySelector('input[id*=":r_input"]');
    const resultTable  = document.querySelector('[id*=":table_result"], [id$=":table_result"], #table_result');
    const submitBtn = document.querySelector('[id*=":submitBtn"]') ||
        document.querySelector('input[type="submit"]');

    function getR() {
        if (rSpinner && rSpinner.value != "") {
            let r = parseFloat(rSpinner.value.replace(',', '.'));
            if (r > 3){
                r = 3;
            }
            if (r < 0){
                r = 0;
            }
            return r;
        }
        return -1;
    }

    function toCanvasX(x) {
        return canvas.width / 2 + x * 50;
    }

    function toCanvasY(y) {
        return canvas.height / 2 - y * 50;
    }

    function fromCanvasX(canvasX) {
        return (canvasX - canvas.width / 2) / 50;
    }

    function fromCanvasY(canvasY) {
        return -(canvasY - canvas.height / 2) / 50;
    }

    function drawGrid() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.strokeStyle = '#e0e0e0';
        ctx.lineWidth = 1;

        for (let x = -4; x <= 4; x++) {
            if (x === 0) continue;
            ctx.beginPath();
            ctx.moveTo(toCanvasX(x), 0);
            ctx.lineTo(toCanvasX(x), canvas.height);
            ctx.stroke();
        }

        for (let y = -4; y <= 4; y++) {
            if (y === 0) continue;
            ctx.beginPath();
            ctx.moveTo(0, toCanvasY(y));
            ctx.lineTo(canvas.width, toCanvasY(y));
            ctx.stroke();
        }
    }

    function drawAxes() {
        ctx.strokeStyle = '#000';
        ctx.lineWidth = 2;
        ctx.font = 'bold 16px Arial';

        ctx.beginPath();
        ctx.moveTo(0, canvas.height / 2);
        ctx.lineTo(canvas.width, canvas.height / 2);
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(canvas.width / 2, 0);
        ctx.lineTo(canvas.width / 2, canvas.height);
        ctx.stroke();

        ctx.fillStyle = '#000';

        for (let x = -4; x <= 4; x++) {
            if (x === 0) continue;
            ctx.fillText(x.toString(), toCanvasX(x) - 5, canvas.height / 2 + 20);
            ctx.beginPath();
            ctx.moveTo(toCanvasX(x), canvas.height / 2 - 5);
            ctx.lineTo(toCanvasX(x), canvas.height / 2 + 5);
            ctx.stroke();
        }

        for (let y = -4; y <= 4; y++) {
            if (y === 0) continue;
            ctx.fillText(y.toString(), canvas.width / 2 + 15, toCanvasY(y) + 5);
            ctx.beginPath();
            ctx.moveTo(canvas.width / 2 - 5, toCanvasY(y));
            ctx.lineTo(canvas.width / 2 + 5, toCanvasY(y));
            ctx.stroke();
        }

        ctx.fillText('0', canvas.width / 2 - 15, canvas.height / 2 + 20);
    }

    function drawArea(R) {
        if (R <= 0) return;
        ctx.beginPath();

        ctx.arc(toCanvasX(0), toCanvasY(0), R * 25, 0, -Math.PI / 2, true);
        ctx.lineTo(toCanvasX(0), toCanvasY(0));

        ctx.moveTo(toCanvasX(0), toCanvasY(0));
        ctx.lineTo(toCanvasX(0), toCanvasY(-R));
        ctx.lineTo(toCanvasX(R), toCanvasY(0));
        ctx.closePath();


        ctx.moveTo(toCanvasX(0), toCanvasY(0));
        ctx.lineTo(toCanvasX(-R/2), toCanvasY(0));
        ctx.lineTo(toCanvasX(-R/2), toCanvasY(-R));
        ctx.lineTo(toCanvasX(0), toCanvasY(-R));
        ctx.closePath();

        ctx.fillStyle = 'rgba(51, 119, 221, 0.8)';
        ctx.fill();
        ctx.strokeStyle = '#3377dd';
        ctx.lineWidth = 2;
        ctx.stroke();
    }

    function drawMarker(X, Y, result) {
        ctx.save();
        ctx.beginPath();
        ctx.arc(toCanvasX(X), toCanvasY(Y), 5, 0, Math.PI * 2);

        if (result === true || result === "true" || result === "Попадание") {
            ctx.fillStyle = '#33dd77';
        } else {
            ctx.fillStyle = '#ff0000';
        }

        ctx.fill();
        ctx.strokeStyle = '#000';
        ctx.lineWidth = 1;
        ctx.stroke();
        ctx.restore();
    }

    function drawMarkersFromBean() {
        const resultTable = document.querySelector('[id*=":table_result"], [id$=":table_result"], #table_result');
        if (!resultTable){
            return;
        }

        const rows = resultTable.querySelectorAll('tr');
        rows.forEach(row => {
            const cells = row.querySelectorAll('td');
            if (cells.length >= 4) {
                const x = parseFloat(cells[0].textContent);
                const y = parseFloat(cells[1].textContent);
                const result = cells[3].textContent.trim();
                drawMarker(x, y, result);
            }
        });
    }

    function drawGraph() {
        const R = getR();
        drawGrid();
        drawArea(R);

        ctx.fillStyle = '#ff0000';
        ctx.font = 'bold 16px Arial';
        if (R <= 0) {
            ctx.fillText('R - не выбрано', 20, 30);
        } else {
            ctx.fillText(`R = ${R}`, 20, 30);
        }

        drawAxes();
        drawMarkersFromBean();
    }

    async function handleCanvasClick(event) {
        const R = getR();

        if (R <= 0) {
            showNotification("Необходимо выбрать R для взаимодействия с графиком", "error")
            return;
        }

        const rect = canvas.getBoundingClientRect();
        const canvasX = event.clientX - rect.left;
        const canvasY = event.clientY - rect.top;

        let mathX = fromCanvasX(canvasX);
        let mathY = fromCanvasY(canvasY);

        mathX = Math.round(mathX);

        if (mathX < -4) mathX = -4;
        if (mathX > 4) mathX = 4;
        if (mathY < -3) mathY = -3;
        if (mathY > 5) mathY = 5;


        if (xInputs.length > 0) {
            const xValue = mathX.toString();
            xInputs.forEach(input => {
                if (input.value === xValue) {
                    input.checked = true;
                }
            });
        }

        if (yInput) {
            yInput.value = mathY.toFixed(2);
        }

        if (form) {
            const submitEvent = new Event('submit', { bubbles: true, cancelable: true });
            form.dispatchEvent(submitEvent);
            if (submitBtn) {
                submitBtn.click();
            }
        }
        setTimeout(() => {
            drawMarkersFromBean();
        }, 50);
    }

    function init() {
        drawGraph();

        canvas.addEventListener('click', handleCanvasClick);

        if (resultTable){
            resultTable.addEventListener('change', function (){
                drawGraph();
            });
        }
        if (submitBtn) {
            submitBtn.addEventListener('click', (event) => {
                setTimeout(() => {
                    drawGraph();
                }, 50);
            });
        }

        const buttons = document.querySelectorAll('button');
        buttons.forEach(button => {
            button.addEventListener('click', (event) => {
                setTimeout(() => {
                    drawGraph();
                }, 50);
            });
        });

        const rRadios = document.querySelectorAll('input[name*=":r"]');
        rRadios.forEach(radio => {
            radio.addEventListener('change', function() {
                drawGraph();
            });
        });
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

});

function validateBeforeAjax() {
    let xValue = getSelectedX();

    if (xValue === null || xValue === undefined || xValue === '') {
        showNotification("Выберите значение X", 'error');
        return false;
    }

    if (rSpinner === null || rSpinner.value === null || xValue === undefined || rSpinner.value === ''){
        showNotification("Выберите значение R", 'error');
        return false;
    }
    return true;
}

function showNotification(message, type) {
    let container = document.getElementById('notifications');
    if (!container) {
        container = document.createElement('div');
        container.id = 'notifications';
        container.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 1000;';
        document.body.appendChild(container);
    }

    const notify = document.createElement('div');
    notify.className = `notification ${type}`;
    notify.textContent = message;
    notify.style.cssText = `
        padding: 10px 15px;
        margin-bottom: 10px;
        border-radius: 4px;
        color: white;
        font-family: Arial, sans-serif;
    `;

    if (type === 'success') {
        notify.style.backgroundColor = '#4CAF50';
    } else if (type === 'error') {
        notify.style.backgroundColor = '#f44336';
    } else {
        notify.style.backgroundColor = '#2196F3';
    }

    container.appendChild(notify);

    setTimeout(() => {
        notify.remove();
    }, 3000);
}