document.addEventListener("DOMContentLoaded", function() {
    const addButton = document.getElementById("addBtn");
    const todo_list = document.getElementById("todo_list");

    // 1️⃣ 從後端取得現有 ToDo 清單
    fetch('/api/todos')
        .then(res => res.json())
        .then(data => data.forEach(todo => addTodoItemFromServer(todo)))
        .catch(err => console.error("取得 ToDo 清單失敗:", err));

    // 2️⃣ 新增按鈕事件
    addButton.addEventListener("click", addTodoItems);

    function addTodoItems() {
        const inputValue = document.getElementById("inputField").value.trim();
        if (!inputValue) { alert("請輸入內容！"); return; }

        fetch('/api/todos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content: inputValue })
        })
            .then(res => res.json())
            .then(todo => {
                addTodoItemFromServer(todo);
                document.getElementById("inputField").value = "";
            })
            .catch(err => console.error("新增 ToDo 失敗:", err));
    }

    function addTodoItemFromServer(todo) {
        const li = document.createElement("li");
        li.className = "item";
        li.dataset.id = todo.id;
        if (todo.done) li.classList.add("checked");

        li.innerHTML = `
            <input type="checkbox" class="checkBtn" ${todo.done ? "checked" : ""}>
            <label class="editable">${todo.title}</label>
            <button class="deleteBtn">x</button>
        `;

        const firstUnchecked = todo_list.querySelector("li:not(.checked)");
        if (firstUnchecked) todo_list.insertBefore(li, firstUnchecked);
        else todo_list.prepend(li);

        li.querySelector(".deleteBtn").addEventListener("click", deleteTodoItems);
        li.querySelector(".editable").addEventListener("click", editTodoItem);
        li.querySelector(".checkBtn").addEventListener("change", toggleDone);
    }

    function toggleDone(event) {
        const li = event.target.closest("li");
        const todoId = li.dataset.id;
        const done = event.target.checked;

        li.classList.toggle("checked", done);

        const firstUnchecked = todo_list.querySelector("li:not(.checked)");
        if (!done && firstUnchecked) todo_list.insertBefore(li, firstUnchecked);
        else if (done) todo_list.appendChild(li);

        fetch(`/api/todos/${todoId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ done })
        }).catch(err => console.error("更新 ToDo 狀態失敗:", err));
    }

    function deleteTodoItems(event) {
        const li = event.target.closest("li");
        const todoId = li.dataset.id;

        fetch(`/api/todos/${todoId}`, { method: 'DELETE' })
            .then(() => li.remove())
            .catch(err => console.error("刪除 ToDo 失敗:", err));
    }

    function editTodoItem(event) {
        const label = event.target;
        const li = label.closest("li");
        const todoId = li.dataset.id;
        const currentText = label.textContent;

        const input = document.createElement("input");
        input.type = "text";
        input.value = currentText;
        input.className = "edit-input";
        input.style.width = "100%";
        input.style.border = "1px solid #ccc";
        input.style.padding = "2px 5px";
        input.style.fontSize = "inherit";
        input.style.fontFamily = "inherit";

        label.style.display = "none";
        label.parentNode.insertBefore(input, label);
        input.focus();
        input.select();

        function saveEdit() {
            const newText = input.value.trim();
            if (newText && newText !== currentText) {
                label.textContent = newText;
                fetch(`/api/todos/${todoId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ title: newText })
                }).catch(err => console.error("更新 ToDo 標題失敗:", err));
            }
            label.style.display = "inline";
            input.remove();
        }

        function cancelEdit() {
            label.style.display = "inline";
            input.remove();
        }

        input.addEventListener("blur", saveEdit);
        input.addEventListener("keydown", function(e) {
            if (e.key === "Enter") { e.preventDefault(); saveEdit(); }
            else if (e.key === "Escape") { e.preventDefault(); cancelEdit(); }
        });
    }
});
