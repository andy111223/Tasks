document.getElementById('survey-form').addEventListener('submit', function(event) {
    event.preventDefault();

    // Previous Questions
    // ...

    let question4 = document.querySelector('input[name="question4"]:checked').value;
    let question5 = document.getElementById('question5').value;
    let question6 = document.querySelector('input[name="question6"]:checked').value;

    console.log('Question 4: ' + question4);
    console.log('Question 5: ' + question5);
    console.log('Question 6: ' + question6);

    // Send this data to your server for processing...
});

