import React, { Fragment, useState, useEffect } from "react";
import styled from "styled-components";
import Popup from "reactjs-popup";
import AddActionDialog from "./AddActionDialog";
import AddActionLogDialog from "./AddActionLogDialog";
import {
  getActions,
  getActionLogs,
  deleteAction,
  deleteActionLog,
} from "./actionApi";

const colors = {
  lightBlue: "#61dafb",
  veryLightBlue: "#C9F1FC",
  white: "white",
  gray: "gray",
};

const Header = styled.h3`
  margin-top: 0px;
  margin-bottom: 12px;
`;

const HeaderText = styled.span`
  color: ${colors.lightBlue};
  margin-right: 4px;
`;

const AppContainer = styled.div`
  background-color: #282c34;
  min-height: 100vh;
  display: flex;
  flex-direction: table;
  align-items: center;
  justify-content: center;
  font-size: calc(4px + 2vmin);
  color: white;
`;

const Add = styled.button`
  color: ${colors.lightBlue} !important;
  background: none !important;
  padding: 0 !important;
  font-size: calc(4px + 2vmin) !important;

  border-color: none !important;
  border-style: none !important;
  outline: none !important;

  user-select: none;
  -webkit-user-select: none;

  &:hover {
    color: ${colors.veryLightBlue};
    cursor: pointer;
  }
`;

const TableContainer = styled.div`
  border-radius: 16px;
  border: 1px solid ${colors.gray};
  padding: 12px;
  margin-right: 12px;
`;

const Table = styled.div`
  margin-bottom: 12px;
  height: 400px;
  width: 400px;
`;

const RowHeader = styled.div`
  margin-bottom: 4px;
  font-weight: bold;
  color: #61dafb;
`;

const Row = styled.div`
  margin-bottom: 4px;
  color: ${(props) => (props.selected ? colors.lightBlue : colors.white)};
  &:hover {
    color: ${colors.lightBlue};
    cursor: pointer;
  }
`;

const Column = styled.div`
  width: 150px;
  text-align: left;
  margin-right: 8px;
  display: inline-block;
`;

const ActionColumn = styled.div`
  text-align: right;
  display: inline-block;
`;

function App() {
  const [actions, setActions] = useState([]);
  const [logs, setLogs] = useState();
  const [selectedAction, setSelectedAction] = useState(null);

  const loadActions = () => {
    getActions().then((response) => {
      setActions(response.data);
    });
  };

  const loadActionLogs = () => {
    if (selectedAction) {
      getActionLogs(selectedAction).then((response) => {
        setLogs(response.data);
      });
    }
  };

  useEffect(() => {
    loadActions();
  }, []);

  useEffect(() => {
    loadActionLogs();
  }, [selectedAction]);

  const isSelected = (action) => {
    return selectedAction && selectedAction.id === action.id;
  };

  const renderActions = () => {
    return (
      <TableContainer>
        <Header>
          <HeaderText>Actions</HeaderText>
          <Popup modal trigger={<Add>+</Add>}>
            {(close) => (
              <AddActionDialog closeDialog={close} onSaved={loadActions} />
            )}
          </Popup>
        </Header>
        <Table>
          <RowHeader>
            <Column>Action</Column>
            <Column>Unit</Column>
            <ActionColumn></ActionColumn>
          </RowHeader>
          {actions.map((action) => (
            <Row
              key={action.id}
              selected={isSelected(action)}
              onClick={() => setSelectedAction(action)}
            >
              <Column>{action.name}</Column>
              <Column>{action.unit}</Column>
              <ActionColumn>
                <Add
                  onClick={async () => {
                    await deleteAction(action);
                    loadActions();
                    setSelectedAction(null);
                    setLogs(null);
                  }}
                >
                  -
                </Add>
              </ActionColumn>
            </Row>
          ))}
        </Table>
      </TableContainer>
    );
  };

  const renderLogs = () => {
    return (
      <TableContainer>
        <Header>
          <HeaderText>Action Logs</HeaderText>
          <Popup modal trigger={<Add>+</Add>}>
            {(close) => (
              <AddActionLogDialog
                action={selectedAction}
                closeDialog={close}
                onSaved={loadActionLogs}
              />
            )}
          </Popup>
        </Header>
        <Table>
          {!logs && <div>Please select an action</div>}
          {logs && (
            <Fragment>
              <RowHeader>
                <Column>Value</Column>
                <Column>Date</Column>
                <ActionColumn></ActionColumn>
              </RowHeader>
              {logs.map((log) => (
                <Row key={log.id}>
                  <Column>{log.value}</Column>
                  <Column>{log.date}</Column>
                  <ActionColumn>
                    <Add
                      onClick={async () => {
                        await deleteActionLog(selectedAction, log);
                        loadActionLogs();
                      }}
                    >
                      -
                    </Add>
                  </ActionColumn>
                </Row>
              ))}
            </Fragment>
          )}
        </Table>
      </TableContainer>
    );
  };

  return (
    <AppContainer>
      {renderActions()}
      {renderLogs()}
    </AppContainer>
  );
}

export default App;
