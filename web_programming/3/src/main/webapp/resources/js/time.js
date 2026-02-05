function startClockUpdate() {
    const intervalId = setInterval(function() {
        const updateButton = document.getElementById('clockForm:updateTimeBtn');
        if (updateButton) {
            updateButton.click();
        } else {
            clearInterval(intervalId);
        }
    }, 8000);
    window.clockUpdateInterval = intervalId;
}
document.addEventListener('DOMContentLoaded', function() {
    startClockUpdate();
    const initialButton = document.getElementById('clockForm:updateTimeBtn');
    if (initialButton) {
        initialButton.click();
    }
});