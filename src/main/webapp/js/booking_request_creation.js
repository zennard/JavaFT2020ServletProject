const form = document.requestForm;
const types = document.getElementsByName("type");

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

    types.forEach((type) => {
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

function createFormGroupDiv() {
    const formGroup = document.createElement("div");
    formGroup.className = "form-group col-xs-6";
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
    /*
    const e = document.getElementById("ddlViewBy");
    const strUser = e.options[e.selectedIndex].text;
     */
    const typeSelect = document.createElement("select");
    typeSelect.name = "typeSelect";
    return typeSelect;
}

