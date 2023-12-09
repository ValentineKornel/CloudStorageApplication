var rows = document.querySelectorAll("#fileTable tr");

rows.forEach(function(row) {
    if(row.id == 'headerTr'){
        return;
    }

    row.addEventListener('mouseover', function() {
        this.style.backgroundColor = 'rgb(213, 212, 212)';
    });

    row.addEventListener('mouseout', function() {
        this.style.backgroundColor = '';
    });
});
