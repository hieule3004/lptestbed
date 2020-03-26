# Testbed structure

1.  **Simulator**
    -   Field: a contract, list of obligation events, log of response
        events and state of agents and resources in the contract.
    -   Method:
        -   run: for each obligation do check and record response.
        -   calculate average value of responses recorded
    -   Note: There is a runner that can stimulate many contracts as
        well as the same contract many times at once.

2.  **Event** Contains timestamp to order events happened.

3.  **Obligation**
    -   Field: enum type and clause which specify the obligation.
    -   Method:
        -   check: get reply from agent in clause, if response is ok, do
            action, return response to be recorded.
        -   action: change state of current simulation given response
            value.

4.  **Response**
    -   Field: value object
    -   Method:
        -   flag if obligation is fulfilled with ok
        -   a way to transform value to a numerical value to calculate
            trust/reputation

5.  **Contract**
    -   Field: List of clauses each associated with a list of obligation
        events to be generated.
    -   Method:
        -   get list of clauses.
        -   get list of obligation events generated for a clause.

6.  **Clause** Contains agent and resource it concerns and the contract
    which the clause is in.

7.  **Agent**
    -   Field: input data
    -   Method: return response corresponding to obligation event.
    -   Subclass: divide to Consumer and Provider, the latter owns some
        Resource(s).

8.  **Resource** Contains input data and its owner.