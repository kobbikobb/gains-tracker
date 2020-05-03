import axios from "axios";

const BASE_URL = "http://localhost:3000/";
const ACTIONS_URL = `${BASE_URL}actions`;

export function getActions() {
  return axios.get(ACTIONS_URL);
}

export function getActionLogs({ id }) {
  return axios.get(`${ACTIONS_URL}/${id}/logs`);
}

export function createAction({ name, unit }) {
  return axios.post(ACTIONS_URL, {
    name,
    unit,
  });
}

export function createActionLog(action, { value, date }) {
  return axios.post(`${ACTIONS_URL}/${action.id}/logs`, {
    value,
    date,
  });
}

export function deleteAction({ id }) {
  return axios.delete(`${ACTIONS_URL}/${id}`);
}

export function deleteActionLog(action, { id }) {
  return axios.delete(`${ACTIONS_URL}/${action.id}/logs/${id}`);
}
