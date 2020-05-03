import React, { useState } from "react";
import { createAction } from "./actionApi";

export default ({ closeDialog, onSaved }) => {
  const [name, setName] = useState("");
  const [unit, setUnit] = useState("");

  const handleSave = (event) => {
    createAction({
      name,
      unit,
    })
      .then(() => {
        closeDialog();
        onSaved();
      })
      .catch((error) => console.log("error", error));

    event.preventDefault();
  };

  return (
    <form style={{ color: "red" }} onSubmit={handleSave}>
      <input
        type="text"
        value={name}
        placeholder={"Name"}
        onChange={(event) => setName(event.target.value)}
      />
      <br />
      <input
        type="text"
        value={unit}
        placeholder={"Unit"}
        onChange={(event) => setUnit(event.target.value)}
      />
      <br />
      <button>Save</button>
    </form>
  );
};
