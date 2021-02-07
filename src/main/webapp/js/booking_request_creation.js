const form = document.requestForm;
const types = document.getElementsByName("type");
const errorPanel = document.getElementById("error-panel");
let usedTypes = [];

document.getElementById("submitBtn").addEventListener("click", onSubmit);

function addInputRow() {
    const inputForm = createInputForm();
    form.appendChild(inputForm);
}

function createInputForm() {
    const div = createDiv();
    const bedsCountFormGroup = createFormGroupDiv();
    const typeFormGroup = createFormGroupDiv();
    const bedsCountInput = createBedsCountInput("1", "5");
    const typeSelect = createTypeSelect();

    types.forEach(type => {
        console.log("type:");
        console.log(type);
        const option = createOption(type.value, type.dataset.type);
        typeSelect.appendChild(option);
    })

    bedsCountFormGroup.appendChild(bedsCountInput);
    typeFormGroup.appendChild(typeSelect);

    div.appendChild(bedsCountFormGroup);
    div.appendChild(typeFormGroup);

    console.log("div:");
    console.log(div);

    return div;
}

function onSubmit() {
    const bedsCountInputElems = document.getElementsByName("bedsCountInput");
    const selectTypeElems = document.getElementsByName("typeSelect");

    console.log(bedsCountInputElems);
    bedsCountInputElems.forEach(elem => console.log(elem.value.length));
    console.log(selectTypeElems);

    if (validate(bedsCountInputElems, selectTypeElems)) {
        form.submit();
    }
}

function validate(bedsCountInputElems, selectTypeElems) {
    clearErrorPanel();
    if (!bedsCountInputElems || !selectTypeElems
        || bedsCountInputElems.length === 0 || selectTypeElems.length === 0) {
        const errorNode = createErrorNode("Please add demands in your request!");
        errorPanel.appendChild(errorNode);
        return false;
    }
    if (Array.from(bedsCountInputElems).filter(elem => !elem.value || elem.value === 0).length > 0
        || bedsCountInputElems.length !== selectTypeElems.length) {
        const errorNode = createErrorNode("Some required fields are not filled!");
        errorPanel.appendChild(errorNode);
        return false;
    }
    if (selectTypeElems.length > types.length) {
        const errorNode = createErrorNode(`There is too much unnecessary fields, 
        expected ${types.length}, but got ${selectTypeElems.length}`);
        errorPanel.appendChild(errorNode);
        return false;
    }

    const selectedTypes = Array.from(selectTypeElems).map(select => select.value);
    if (selectedTypes.length !== new Set(selectedTypes).size) {
        const errorNode = createErrorNode("Types have duplicates!");
        errorPanel.appendChild(errorNode);
        return false;
    }
    return form.validate();

}

function createErrorNode(text) {
    const paragraph = document.createElement("p");
    paragraph.textContent = text;
    return paragraph;
}

function clearErrorPanel() {
    errorPanel.innerHTML = "";
}

function createFormGroupDiv() {
    const formGroup = document.createElement("div");
    formGroup.className = "form-group col-sm-6";
    return formGroup;
}

function createDiv() {
    const div = document.createElement("div");
    div.className = "row"
    return div;
}

function createBedsCountInput(min, max) {
    const bedsCountInput = document.createElement("input");
    bedsCountInput.type = "number";
    bedsCountInput.name = "bedsCountInput";
    bedsCountInput.min = min;
    bedsCountInput.max = max;

    return bedsCountInput;
}

function createOption(type, value) {
    const typeOption = document.createElement("option");
    typeOption.label = type;
    typeOption.value = value;
    return typeOption;
}

function createTypeSelect() {
    const typeSelect = document.createElement("select");
    typeSelect.name = "typeSelect";
    return typeSelect;
}

