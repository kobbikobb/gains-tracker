import React, { useState } from "react";
import { createActionLog } from "./actionApi";

export default ({ action, closeDialog, onSaved }) => {
  const [value, setValue] = useState("");
  const [date, setDate] = useState("");

  const handleSave = (event) => {
    createActionLog(action, {
      value,
      date,
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
        value={value}
        placeholder={"Value"}
        onChange={(event) => setValue(event.target.value)}
      />
      <br />
      <input
        type="text"
        value={date}
        placeholder={"Date"}
        onChange={(event) => setDate(event.target.value)}
      />
      <br />
      <button>Save</button>
    </form>
  );
};
